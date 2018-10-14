package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.Path
import scala.collection.mutable

class PathMatcher(ctx: AppContext) {
  def matchPath(path: Path): PathMatch = {
    var lineNo = 0 // lines are 1-indexed
    val lineMatches: Seq[LineMatch] = PathReader(path)
      .foldLeft(Seq.empty[LineMatch]) {
        case (acc, line) =>
          lineNo += 1

          if (line.length > ctx.maxLineLen) {
            acc
          } else if (line.indexOf(Const.nullChar) > 0) {
            return PathMatch(path, acc) // stop searching file
          } else {
            val regionMatches = mutable.Seq.newBuilder[RegionMatch]
            for (m <- ctx.query.findAllIn(line).matchData) {
              regionMatches += RegionMatch(m.start, m.end)
            }

            // only add match if there was at least one line match
            val tmp = regionMatches.result()
            if (tmp.isEmpty) {
              acc
            } else {
              acc :+ LineMatch(line, lineNo, regionMatches.result())
            }
          }
      }
    PathMatch(path, lineMatches)
  }
}
