package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.Path

object PathMatches {
  def pprint(ctx: AppContext, path: Path, pathMatches: Seq[PathMatch]): String = {
    val builder = StringBuilder.newBuilder
    builder.append(Text.Green(path.toString) + Const.newLine)
    pathMatches.foreach { pathMatch =>
      pathMatch.matches.foreach { lineMatch: LineMatch =>
        val lineNumber = Text.Yellow("%5d".format(lineMatch.lineNo))
        val lineRegion = Text.Green("[%5d,%5d]".format(lineMatch.start, lineMatch.end))
        val content = s"$lineNumber: $lineRegion ${ pathMatch.line }"

        builder.append(content + Const.newLine)
      }
    }
    builder.result()
  }
}

case class PathMatch(line: String, matches: Seq[LineMatch])

case class LineMatch(start: Int, end: Int, lineNo: Int)
