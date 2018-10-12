package com.cevaris.ag4s
package cli


import com.twitter.util.{Return, Throw, Try}
import org.apache.commons.cli
import collection.JavaConverters._
import org.apache.commons.cli.{CommandLine, DefaultParser, HelpFormatter, Options}

object CommonCliArgs {
  private val parser = new DefaultParser

  private val opts = new Options()

  def parse(args: Array[String]): Try[CommonCliArgs] = {
    println(args.mkString(", "))
    opts.addOption("v", "verbose")
    opts.addOption("h", "help", false, "print help description")
    opts.addOption("G", true, "filter by path")
    opts.addOption(
      cli.Option
        .builder(null)
        .longOpt("pager")
        .hasArg
        .desc("pipe to user paging cli app")
        .numberOfArgs(cli.Option.UNLIMITED_VALUES)
        .valueSeparator
        .build
    )


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
        CommonCliArgs(
          isDebug = cl.hasOption("v"),
          pagerCommand = optional(cl, "pager"),
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

case class CommonCliArgs(
  isDebug: Boolean,
  pagerCommand: Option[String],
  pathFilter: Option[String],
  params: Seq[String],
) extends Args
