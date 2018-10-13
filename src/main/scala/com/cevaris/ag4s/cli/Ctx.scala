package com.cevaris.ag4s
package cli


import com.twitter.util.{Return, Throw, Try}
import org.apache.commons.cli.{CommandLine, DefaultParser, HelpFormatter, Options}
import scala.collection.JavaConverters._

object Ctx {
  private val parser = new DefaultParser

  private val opts = new Options()

  def parse(args: Array[String]): Try[Ctx] = {
    opts.addOption("v", "verbose")
    opts.addOption("h", "help", false, "print help description")
    opts.addOption("G", true, "filter by path")

    Try(parser.parse(opts, args))
      .flatMap { cl =>
        if (cl.hasOption("h")) {
          val formatter = new HelpFormatter
          formatter.printHelp("a4js", opts)
        }
        Return(cl)
      }
      .flatMap { cl =>
        if (cl.getArgList.isEmpty) {
          Throw(new IllegalArgumentException("err: no search params provided"))
        } else {
          Return(cl)
        }
      }
      .map { cl =>
        Ctx(
          isDebug = cl.hasOption("v"),
          pathFilter = optional(cl, "G"),
          params = cl.getArgList.asScala
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
  params: Seq[String]
) extends AppContext
