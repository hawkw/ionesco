lazy val commonSettings = Seq(
  organization := "me.hawkweisman",
  version := "0.0.1",
  scalaVersion := "2.11.8"
)

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
