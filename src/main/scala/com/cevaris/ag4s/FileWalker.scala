package com.cevaris.ag4s

import com.cevaris.ag4s.cli.AppContext
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Path, SimpleFileVisitor}
import scala.io.Source


class FileWalker(ctx: AppContext) extends SimpleFileVisitor[Path] {

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

    ctx.logger.debug(s"match - $file")
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
}
