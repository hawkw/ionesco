logLevel := Level.Warn

resolvers += // Resolve the Scoverage plugin from the Typesafe releases repo
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

// Scoverage plugin for collecting code coverage data from builds
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")

// Plugin for reporting coverage data to Codacy
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.0")

// Plugin for using VersionEye to check dependency versions and licenses
addSbtPlugin("com.versioneye" % "sbt-versioneye-plugin" % "0.2.0")