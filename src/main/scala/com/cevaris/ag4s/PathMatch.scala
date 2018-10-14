package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.Path

object PathMatches {
  def pprint(ctx: AppContext, path: Path, pathMatch: PathMatch): String = {
    val builder = StringBuilder.newBuilder
    builder.append(Text.Green(path.toString) + Const.newLine)

    pathMatch.matches.foreach { lineMatch: LineMatch =>
      val coloredLine: String = {
        val line = lineMatch.line
        var idx = 0
        val str = lineMatch.regions.foldLeft(new StringBuilder) {
          case (acc, region) =>
            if (region.start > idx) {
              // if match is later in the string, first prepend
              acc.append(line.substring(idx, region.start))
            }

            idx += region.end // increment index to end of region
            acc.append(Text.Green(line.substring(region.start, region.end)))
        }

        str.result()
      }
      val lineNumber = Text.Yellow("%5d".format(lineMatch.lineNo))
      val content = s"$lineNumber: $coloredLine"

      builder.append(content + Const.newLine)
    }

    builder.result()
  }
}

case class PathMatch(path: Path, matches: Seq[LineMatch])

case class LineMatch(line: String, lineNo: Int, regions: Seq[RegionMatch])

case class RegionMatch(start: Int, end: Int)
