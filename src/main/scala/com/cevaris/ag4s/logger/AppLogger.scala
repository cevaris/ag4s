package com.cevaris.ag4s.logger

import scala.reflect.ClassTag

object AppLogger {
  // inject custom logger
  def default[T](implicit ct: ClassTag[T]): ScalaLogger = ScalaLogger[T]
}

trait AppLogger {
  def info(message: String): Unit

  def error(message: String, throwable: Throwable): Unit

  def error(message: String): Unit
}
