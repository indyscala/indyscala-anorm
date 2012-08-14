organization := "localhost"

name := "indyscala-anorm"

version := "20120813"

scalaVersion := "2.9.2"

libraryDependencies ++=
  "play" %% "anorm" % "2.1-08072012" ::
  "postgresql" % "postgresql" % "9.1-901.jdbc4" ::
  Nil

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

