package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import com.twitter.concurrent.NamedPoolThreadFactory
import com.twitter.util.FuturePool
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Path, SimpleFileVisitor}
import java.util.concurrent.{SynchronousQueue, ThreadPoolExecutor, TimeUnit}
import scala.io.Source

class PathWalker(ctx: AppContext) extends SimpleFileVisitor[Path] {
  private val pathMatcher = new PathMatcher(ctx)
  private val futurePool = FuturePool(
    new ThreadPoolExecutor(
      0,
      Const.cores - 1,
      60L,
      TimeUnit.SECONDS,
      new SynchronousQueue[Runnable](),
      new NamedPoolThreadFactory("arg4s", makeDaemons = true)
    )
  )

  override def preVisitDirectory(
    dir: Path,
    attrs: BasicFileAttributes
  ): FileVisitResult = {
    FileVisitResult.CONTINUE
  }

  override def visitFile(
    file: Path,
    attrs: BasicFileAttributes
  ): FileVisitResult = {
    if (ctx.pathFilter.isDefined && !file.toString.contains(ctx.pathFilter.get)) {
      ctx.logger.debug(s"skip - $file does not match ${ ctx.pathFilter.get }")
      return FileVisitResult.SKIP_SUBTREE
    }

    if (file.endsWith(".gitignore")) {
      visitGitignore(file)
      return FileVisitResult.SKIP_SUBTREE
    }

    ctx.logger.debug(s"walker-match - $file")
    futurePool {
      val pathMatch = pathMatcher.matchPath(file)
      if (pathMatch.matches.nonEmpty) {
        ctx.logger.info(PathMatches.pprint(ctx, file, pathMatch))
      }
    }

    FileVisitResult.CONTINUE
  }


  // TODO: read and include
  private def visitGitignore(file: Path): Unit = {
    val content = Source.fromFile(file.toFile)
      .getLines()
      .toList
      .map(_.trim)
      .filterNot(_.startsWith("#")) // ignore comments
      .filter(_.nonEmpty) // ignore blank lines
    ctx.logger.debug(s"gitignore - $content")
  }

  /**
   * Wait for any still running Futures
   */
  def waitFor(): Unit = {
    while(futurePool.numActiveTasks>0){
      Thread.sleep(10)
    }
  }

}
