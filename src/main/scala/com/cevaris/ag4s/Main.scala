package com.cevaris.ag4s

import com.cevaris.ag4s.cli.Ctx
import com.twitter.util.{Return, Throw}
import java.nio.file.Files

trait ShutdownException extends RuntimeException {
  val exitCode: Int
}

case class SuccessShutdown(override val exitCode: Int = 0) extends ShutdownException

object Main extends App {

  Ctx.parse(args) match {
    case Return(ctx) =>
      ctx.logger.debug(ctx.toString)

      val walker = new FileWalker(ctx)
      ctx.paths.foreach { path =>
        Files.walkFileTree(path, walker)
      }

    case Throw(t: ShutdownException) =>
      System.exit(t.exitCode)
    case Throw(t) =>
      throw t
  }
}
