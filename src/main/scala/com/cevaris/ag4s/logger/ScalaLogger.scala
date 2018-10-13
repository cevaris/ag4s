package com.cevaris.ag4s.logger

import com.typesafe.scalalogging.Logger
import scala.reflect.ClassTag

object ScalaLogger {
  def apply[T](implicit ct: ClassTag[T]): ScalaLogger = {
    new ScalaLogger(Logger[T])
  }
}

class ScalaLogger(logger: Logger) extends AppLogger {
  override def info(message: String): Unit =
    logger.info(message)

  override def error(message: String, throwable: Throwable): Unit =
    logger.error(message, throwable)

  override def error(message: String): Unit =
    logger.error(message)
}
