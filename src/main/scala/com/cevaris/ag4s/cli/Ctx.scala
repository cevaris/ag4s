package com.cevaris.ag4s
package cli


import com.cevaris.ag4s.logger.AppLogger
import com.twitter.util.{Return, Throw, Try}
import java.nio.file.{Files, Path, Paths}
import org.apache.commons.cli.{CommandLine, DefaultParser, HelpFormatter, Options}
import scala.collection.JavaConverters._
import java.nio.file.FileSystems
import java.nio.file.Path

object Ctx {
  private val logger = AppLogger.default[Ctx]

  private val parser = new DefaultParser

  private val opts = new Options()

  def parse(args: Array[String]): Try[Ctx] = {
    opts.addOption("v", "verbose", false, "outputs debug info")
    opts.addOption("h", "help", false, "print help description")
    opts.addOption("G", true, "filter by path")

    Try(parser.parse(opts, args))
      .flatMap { cl =>
        if (cl.hasOption("h")) {
          val formatter = new HelpFormatter
          formatter.printHelp("a4js", opts)
          Throw(SuccessShutdown())
        } else {
          Return(cl)
        }
      }
      .flatMap { cl =>
        if (cl.getArgList.isEmpty) {
          Throw(new IllegalArgumentException("err: no search params provided"))
        } else {
          Return(cl)
        }
      }
      .map { cl =>
        val params = cl.getArgList.asScala
        // validated above
        val query = params.head
        val paths = params.tail.flatMap { pathName: String =>
          val path: Path = Paths.get(pathName)
          if (Files.exists(path)) {
            Some(path.toAbsolutePath.normalize())
          } else {
            logger.error(String.format("%s path does not exist", path.toString))
            None
          }
        }
        // assign current working directory if non provided
        val updatePaths = if (paths.isEmpty) {
          Seq(FileSystems.getDefault.getPath(".").toAbsolutePath.normalize())
        } else {
          paths
        }

        Ctx(
          isDebug = cl.hasOption("v"),
          pathFilter = optional(cl, "G"),
          paths = updatePaths,
          query = query
        )
      }

  }

  private def optional(
    commandLine: CommandLine,
    optionName: String,
    default: Option[String] = None
  ): Option[String] = {
    if (commandLine.hasOption(optionName)) {
      val arg = commandLine.getOptionValue(optionName)
      if (arg.trim.nonEmpty) {
        Some(commandLine.getOptionValue(optionName))
      } else {
        None
      }
    } else {
      default
    }
  }
}

case class Ctx(
  isDebug: Boolean,
  pathFilter: Option[String],
  paths: Seq[Path],
  query: String
) extends AppContext
