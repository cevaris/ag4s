package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.Path

class PathMatcher(ctx: AppContext) {

  def matchPath(path: Path): Seq[String] = {
    PathReader(path)
      .foldLeft(Seq.empty[String]) { case (acc, line) =>
        val matcher = ctx.query.matcher(line)
        if (matcher.find()) {
          acc :+ line
        } else {
          acc
        }
      }
  }
}
