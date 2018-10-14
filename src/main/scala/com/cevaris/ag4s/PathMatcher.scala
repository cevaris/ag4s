package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.Path
import scala.collection.mutable

class PathMatcher(ctx: AppContext) {

  val nullChar = 0x00

  def matchPath(path: Path): Seq[PathMatch] = {
    var lineNo = 0 // lines are 1-indexed
    PathReader(path)
      .foldLeft(Seq.empty[PathMatch]) { case (acc, line) =>
        lineNo += 1

        if (line.length > ctx.maxLineLen) {
          acc
        }
        else if (line.indexOf(nullChar) > 0) {
          acc
        } else {
          val builder = mutable.Seq.newBuilder[LineMatch]
          for (m <- ctx.query.findAllIn(line).matchData) {
            builder += LineMatch(
              m.start,
              m.end,
              lineNo
            )
          }

          // only add match if there was at least one line match
          val tmp = builder.result()
          if (tmp.isEmpty) {
            acc
          } else {
            acc :+ PathMatch(line, builder.result())
          }
        }
      }
  }
}
