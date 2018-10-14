package com.cevaris.ag4s.logger

import com.typesafe.scalalogging.Logger

class ScalaLogger(isDebug: Boolean) extends AppLogger {
  private val logger = Logger[ScalaLogger]

  override def info(message: String): Unit =
    logger.info(message)

  override def error(message: String, throwable: Throwable): Unit =
    logger.error(message, throwable)

  override def error(message: String): Unit =
    logger.error(message)

  override def debug(message: String): Unit =
    if (isDebug) {
      logger.debug(message)
    }
}
