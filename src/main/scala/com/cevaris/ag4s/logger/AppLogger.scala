package com.cevaris.ag4s.logger

trait AppLogger {
  def info(message: Any*): Unit

  def error(throwable: Throwable, message: Any*): Unit

  def close(): Unit
}
