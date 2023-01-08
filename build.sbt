ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scala-feature-toggles"
  )

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.14.0",
  "org.scalatest" %% "scalatest" % "3.2.14" % "test"
)