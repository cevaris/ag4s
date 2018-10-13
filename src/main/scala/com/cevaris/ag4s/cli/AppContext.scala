package com.cevaris.ag4s.cli

trait AppContext {
  def isDebug: Boolean

  def pathFilter: Option[String]

  def params: Seq[String]
}
