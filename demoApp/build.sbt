name := "MyDemoProgram"

version := "0.1"

scalaVersion := "2.13.10"

libraryDependencies += "dev.zio" %% "zio-http" % "3.0.0-RC1"

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}