package com.cevaris.ag4s.logger

import com.typesafe.scalalogging.Logger
import scala.reflect.ClassTag

object ScalaLogger {
  def apply[T](implicit ct: ClassTag[T]): ScalaLogger = {
    new ScalaLogger(Logger[T])
  }
}

class ScalaLogger(logger: Logger) extends AppLogger {
  override def info(message: Any*): Unit = {
    logger.info(message.mkString(" "))
  }

  override def error(t: Throwable, message: Any*): Unit = {
    logger.error(message.mkString(" "), t)
  }
}
