package com.cevaris.ag4s
package logger

trait AppLogger {
  def debug(message: String): Unit

  def info(message: String): Unit

  def error(message: String, throwable: Throwable): Unit

  def error(message: String): Unit
}
