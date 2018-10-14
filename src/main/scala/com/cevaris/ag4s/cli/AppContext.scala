package com.cevaris.ag4s.cli

import com.cevaris.ag4s.logger.AppLogger
import java.nio.file.Path

trait AppContext {
  def isDebug: Boolean

  def pathFilter: Option[String]

  def paths: Seq[Path]

  def query: String

  // TODO: Move app logger out of AppContext;
  // https://stackoverflow.com/questions/18102898/how-can-i-change-log-level-of-single-logger-in-runtime
  def logger: AppLogger
}
