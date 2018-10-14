package com.cevaris.ag4s

import java.io.{BufferedReader, FileReader}
import java.nio.file.Path

object PathReader {
  def apply(path: Path): Iterator[String] = {
    val bf = new BufferedReader(new FileReader(path.toFile))
    new Iterator[String] {
      private var content: String = _

      override def hasNext: Boolean = {
        content = bf.readLine()
        content != null
      }

      override def next(): String =
        content
    }
  }
}
