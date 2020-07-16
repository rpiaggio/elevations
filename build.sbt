cancelable in Global := true

Global / onChangedBuildSource := ReloadOnSourceChanges

scalaVersion := "2.13.3"

description := "elevations"

scalacOptions += "-Ymacro-annotations"

resolvers += "Gemini Repository" at "https://github.com/gemini-hlsw/maven-repo/raw/master/releases"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.1.1",
  "edu.gemini" %% "gsp-core-model" % "0.2.6",
  "edu.gemini.ocs" %% "edu-gemini-util-skycalc" % "2020001.1.7",
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test,
  "com.disneystreaming" %% "weaver-framework" % "0.4.1" % Test,
  "com.disneystreaming" %% "weaver-scalacheck" % "0.4.1" % Test,
  "edu.gemini" %% "gsp-core-testkit" % "0.2.6" % Test,
  "edu.gemini" %% "gsp-math-testkit" % "0.2.4" % Test,
  "com.47deg" %% "scalacheck-toolbox-datetime" % "0.3.5" % Test
)

testFrameworks += new TestFramework("weaver.framework.TestFramework")

mainClass in (Compile, run) := Some("Main")

scalacOptions ++= Seq("-deprecation")

// don't publish anything
publish := {}
publishLocal := {}
publishArtifact := false
Keys.`package` := file("")
