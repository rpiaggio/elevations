cancelable in Global := true

Global / onChangedBuildSource := ReloadOnSourceChanges

scalaVersion := "2.13.3"

description := "elevations"

scalacOptions += "-Ymacro-annotations"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.1.1",
  "edu.gemini" %% "gsp-core-model" % "0.2.6"
)

mainClass in (Compile, run) := Some("Main")

// don't publish anything
publish := {}
publishLocal := {}
publishArtifact := false
Keys.`package` := file("")
