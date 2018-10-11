name := "ag4s"

version := "0.1"

scalaVersion := "2.12.7"

mainClass := Some("com.cevaris.ag4s.Main")


libraryDependencies ++= Seq(
  "commons-cli" % "commons-cli" % "1.4"
)

// Bloop config https://scalacenter.github.io/bloop/docs/
bloopAggregateSourceDependencies in Global := true
