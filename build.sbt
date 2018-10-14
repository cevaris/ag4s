name := "ag4s"

version := "0.1"

scalaVersion := "2.11.12"

mainClass := Some("com.cevaris.ag4s.Main")


libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.twitter" %% "util-core" % "18.9.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "commons-cli" % "commons-cli" % "1.4"
)

// Bloop config https://scalacenter.github.io/bloop/docs/
bloopAggregateSourceDependencies in Global := true

// For fast CLI startup http://www.scala-native.org/en/v0.3.8/user/sbt.html
// Cannot enable this unless we ditch twitter/cli libs.
//enablePlugins(ScalaNativePlugin)
