package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.Path
import scala.collection.mutable

class PathMatcher(ctx: AppContext) {

  def matchPath(path: Path): Seq[PathMatch] = {
    var lineNo = 0 // lines are 1-indexed
    PathReader(path)
      .foldLeft(Seq.empty[Seq[PathMatch]]) { case (acc, line) =>
        lineNo += 1

        val builder = mutable.Seq.newBuilder[PathMatch]
        for (m <- ctx.query.findAllIn(line).matchData) {
          builder += PathMatch(
            line,
            m.start,
            m.end,
            lineNo
          )
        }
        acc :+ builder.result()
      }
      .flatten
  }
}
