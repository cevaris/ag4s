package com.cevaris.ag4s

import com.cevaris.ag4s.cli.Ctx
import com.twitter.util.{Await, Return, Throw}
import java.nio.file.Files

trait ShutdownException extends RuntimeException {
  val exitCode: Int
}

case class SuccessShutdown(override val exitCode: Int = 0) extends ShutdownException

object Main extends App {

  Ctx(args) match {
    case Return(ctx) =>
      ctx.logger.debug(ctx.toString)

      val walker = new PathWalker(ctx)
      ctx.paths.foreach { path =>
        Files.walkFileTree(path, walker)
      }

    case Throw(t: ShutdownException) =>
      System.exit(t.exitCode)
    case Throw(t) =>
      throw t
  }
}
