package com.cevaris.ag4s

object Text {
  def Red(s: String): String = paint(s, Console.RED)

  def Green(s: String): String = paint(s, Console.GREEN)

  def Yellow(s: String): String = paint(s, Console.YELLOW)

  private def paint(s: String, c: String): String =
    c + s + Console.RESET
}
