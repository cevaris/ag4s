package com.cevaris.ag4s.cli

import java.nio.file.Path

trait AppContext {
  def isDebug: Boolean

  def pathFilter: Option[String]

  def paths: Seq[Path]

  def query: String
}
