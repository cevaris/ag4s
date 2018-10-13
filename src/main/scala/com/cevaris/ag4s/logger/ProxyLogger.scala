package com.cevaris.ag4s.logger


class ProxyLogger(pager: Option[String]) extends AppLogger {
  override def info(message: Any*): Unit = {

  }

  override def error(t: Throwable, message: Any*): Unit = {
  }

  override def close(): Unit = {

  }
}
