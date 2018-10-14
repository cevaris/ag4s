package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import com.cevaris.ag4s.logger.AppLogger
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Path, SimpleFileVisitor}


class FileWalker(ctx: AppContext) extends SimpleFileVisitor[Path] {
  private val logger = AppLogger.default

  override def preVisitDirectory(
    dir: Path,
    attrs: BasicFileAttributes
  ): FileVisitResult = {
    if (ctx.pathFilter.isDefined && !dir.toString.contains(ctx.pathFilter.get)) {
      FileVisitResult.TERMINATE
    } else {
      if (ctx.isDebug) {
        logger.info(s"found path filter match $dir")
      }
      FileVisitResult.CONTINUE
    }
  }

  override def visitFile(
    file: Path,
    attrs: BasicFileAttributes
  ): FileVisitResult = {
    logger.info(s"visiting $file")
    FileVisitResult.CONTINUE
  }
}
