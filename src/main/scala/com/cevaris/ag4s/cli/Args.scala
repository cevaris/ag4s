package com.cevaris.ag4s.cli

trait Args {
  def isDebug: Boolean
  def pagerCommand: Option[String]
  def pathFilter: Option[String]
  def params: Seq[String]
}
