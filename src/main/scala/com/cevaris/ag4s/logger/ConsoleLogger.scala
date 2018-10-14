package com.cevaris.ag4s.logger

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}

class ConsoleLogger(isDebug: Boolean) extends AppLogger {
  val logger = new BufferedWriter(
    new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out)),
    512
  )

  override def info(message: String): Unit = {
    logger.write(message)
    logger.newLine()
    logger.flush()
  }

  override def error(message: String, throwable: Throwable): Unit = {
    logger.write(message)
    logger.newLine()
    logger.flush()
  }

  override def error(message: String): Unit = {
    logger.write(message)
    logger.newLine()
    logger.flush()
  }

  override def debug(message: String): Unit =
    if (isDebug) {
      logger.write(message)
      logger.newLine()
      logger.flush()
    }
}

