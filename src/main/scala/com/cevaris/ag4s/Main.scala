package com.cevaris.ag4s

import com.cevaris.ag4s.cli.CommonCliArgs
import com.twitter.util.{Return, Throw}

object Main extends App {
  val parsedArgs = CommonCliArgs.parse(args) match {
    case Return(results) => results
    case Throw(t) =>
      System.err.println(t.getMessage)
      System.exit(-1)
  }
  println("hello world", parsedArgs)
  System.exit(0)
}
