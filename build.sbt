cancelable in Global := true

Global / onChangedBuildSource := ReloadOnSourceChanges

scalaVersion := "2.13.3"

description := "elevations"

scalacOptions += "-Ymacro-annotations"

resolvers += "Gemini Repository" at "https://github.com/gemini-hlsw/maven-repo/raw/master/releases"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.1.1",
  "edu.gemini" %% "gsp-core-model" % "0.2.6",
  "edu.gemini.ocs" %% "edu-gemini-util-skycalc" % "2020001.1.7"
)

mainClass in (Compile, run) := Some("Main")

scalacOptions ++= Seq("-deprecation")

// don't publish anything
publish := {}
publishLocal := {}
publishArtifact := false
Keys.`package` := file("")
