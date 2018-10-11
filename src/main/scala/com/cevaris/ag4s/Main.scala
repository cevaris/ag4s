package com.cevaris.ag4s

import org.apache.commons.cli.{DefaultParser, Option, Options}

object Main extends App {
  val parser = new DefaultParser
  val opts = new Options()
  opts.addOption("G", true, "filter by path")
  opts.addOption("D", "debug")

  val pager = Option
    .builder(null)
    .longOpt("pager")
    .hasArg
    .desc("pipe to user paging cli app")
    .numberOfArgs(Option.UNLIMITED_VALUES)
    .valueSeparator
    .build
  opts.addOption(pager)

  val cmdLine = parser.parse(opts, args)

  println("hello world", cmdLine.getArgs)
  System.exit(0)
}
