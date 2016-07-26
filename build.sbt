lazy val commonSettings = Seq(
  organization := "me.hawkweisman",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= testLibraries
)

lazy val version_ScalaTest = "2.2.6"

lazy val testLibraries = Seq(
  "org.scalatest" %% "scalatest" % version_ScalaTest % "test"
)

lazy val core = (project in file("."))
  .settings(commonSettings: _*)
  
lazy val json = (project in file("json"))
  .dependsOn(core)
  .settings(commonSettings: _*)

lazy val nashorn = (project in file("nashorn"))
  .dependsOn(core)
  .settings(commonSettings: _*)
  
lazy val json = (project in file("json"))
  .dependsOn(core)
  .settings(commonSettings: _*)

lazy val nashorn = (project in file("nashorn"))
  .dependsOn(core)
  .settings(commonSettings: _*)
