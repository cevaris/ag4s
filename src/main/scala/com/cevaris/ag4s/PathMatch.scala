package com.cevaris.ag4s

case class PathMatch(line: String, matches: Seq[LineMatch])

case class LineMatch(start: Int, end: Int, lineNo: Int)
