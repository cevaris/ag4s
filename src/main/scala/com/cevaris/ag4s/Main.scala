package com.cevaris.ag4s

import com.cevaris.ag4s.cli.Ctx
import com.twitter.util.{Return, Throw}

object Main extends App {
  Ctx.parse(args) match {
    case Return(ctx) =>
      println(ctx)

    case Throw(t) =>
      System.err.println(t.getMessage)
      System.exit(-1)
  }
}
