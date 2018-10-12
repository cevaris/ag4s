package com.cevaris.ag4s
package cli

import com.twitter.util.Try
import org.apache.commons.cli
import org.apache.commons.cli.{CommandLine, DefaultParser, HelpFormatter, Options}

object CommonCliArgs {
  private val parser = new DefaultParser

  private val opts = new Options()

  def parse(args: Array[String]): Try[CommonCliArgs] = {
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
      .map { cl =>
        if (cl.hasOption("h")) {
          val formatter = new HelpFormatter
          formatter.printHelp("a4js", opts)
        }

        CommonCliArgs(
          isDebug = cl.hasOption("v"),
          pagerCommand = optional(cl, "pager"),
          pathFilter = optional(cl, "G"),
          printHelp = None
        )
      }

  }

  private def optional(
    commandLine: CommandLine,
    optionName: String,
    default: Option[String] = None
  ): Option[String] = {
    if (commandLine.hasOption(optionName)) {
      Some(commandLine.getOptionValue(optionName))
    } else {
      default
    }
  }
}

case class CommonCliArgs(
  isDebug: Boolean,
  pagerCommand: Option[String],
  pathFilter: Option[String],
  printHelp: Option[String]
) extends Args
