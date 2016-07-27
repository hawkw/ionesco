
//--- common settings across all projects -------------------------------------
lazy val commonSettings = Seq(
  organization := "me.hawkweisman",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= testLibraries,
  libraryDependencies += "org.json" % "json" % version_Json
)

//--- settings for the VersionEye plugin --------------------------------------
lazy val versionEyeSettings = Seq(
  existingProjectId in versioneye := "5798c24f74848d0044b814ca",
  baseUrl in versioneye := "https://www.versioneye.com",
  apiPath in versioneye := "/api/v2",
  publishCrossVersion in versioneye := true
)

//--- versions of various dependencies ----------------------------------------
lazy val version_ScalaTest = "2.2.6"
lazy val version_Json = "20160212"

lazy val testLibraries = Seq(
  "org.scalatest" %% "scalatest" % version_ScalaTest % "test"
)

lazy val core = (project in file("."))
  .enablePlugins(VersionEyePlugin)
  .settings(commonSettings: _*)
  .settings(versionEyeSettings: _*)
  
lazy val json = (project in file("json"))
  .enablePlugins(VersionEyePlugin)
  .dependsOn(core)
  .settings(commonSettings: _*)
  .settings(versionEyeSettings: _*)

lazy val nashorn = (project in file("nashorn"))
  .enablePlugins(VersionEyePlugin)
  .dependsOn(core)
  .settings(commonSettings: _*)
  .settings(versionEyeSettings: _*)
