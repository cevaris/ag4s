package com.cevaris.ag4s
package cli

import com.cevaris.ag4s.logger.{AppLogger, ScalaLogger}
import com.twitter.util.{Return, Throw, Try}
import java.nio.file.{FileSystems, Files, Path, Paths}
import java.util.regex.Pattern
import org.apache.commons.cli.{CommandLine, DefaultParser, HelpFormatter, Options}
import scala.collection.JavaConverters._
import scala.util.matching.Regex

object Ctx {
  private val parser = new DefaultParser
  private val options = new Options()

  def apply(args: Array[String]): Try[Ctx] = {
    options.addOption("v", "verbose", false, "outputs debug info")
    options.addOption("h", "help", false, "print help description")
    options.addOption("G", true, "filter by path")

    Try(parser.parse(options, args))
      .flatMap(helpEffect)
      .flatMap(validSearchParamEffect)
      .map { cl =>
        val isDebug = cl.hasOption("v")
        val logger = new ScalaLogger(isDebug)

        val params = cl.getArgList.asScala
        /**
         * Params validated by [[Ctx.validSearchParamEffect]]
         */
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
        // assign current working directory if no paths provided
        val updatePaths = if (paths.isEmpty) {
          Seq(FileSystems.getDefault.getPath(".").toAbsolutePath.normalize())
        } else {
          paths
        }

        Ctx(
          isDebug = isDebug,
          logger = logger,
          pathFilter = optional(cl, "G"),
          paths = updatePaths,
          query = new Regex(query)
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

  // Validators

  type InputEffect = CommandLine => Try[CommandLine]

  private val helpEffect: InputEffect = { cl =>
    if (cl.hasOption("h")) {
      val formatter = new HelpFormatter
      formatter.printHelp("a4js", options)
      Throw(SuccessShutdown())
    } else {
      Return(cl)
    }
  }

  private val validSearchParamEffect: InputEffect = { cl =>
    if (cl.getArgList.isEmpty) {
      Throw(new IllegalArgumentException("no search params provided"))
    } else {
      Try(Pattern.compile(cl.getArgList.get(0)))
        .map(_ => cl)
    }
  }

}

case class Ctx(
  isDebug: Boolean,
  logger: AppLogger,
  pathFilter: Option[String],
  paths: Seq[Path],
  query: Regex
) extends AppContext {
  override def maxLineLen: Int = 1000
}
