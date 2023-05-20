lazy val agent = (project in file("."))
  .settings(
    name := "javaAgent",
    version := "0.1",
    scalaVersion := "2.13.10",
    organization := "Funky Functor Inc.",
    libraryDependencies += "org.javassist" % "javassist" % "3.29.2-GA",
    assembly / assemblyJarName := "demo-agent.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _                        => MergeStrategy.first
    },
    packageOptions += Package.ManifestAttributes(
      "Premain-Class" -> "com.funkyfunctor.internal.instrumentationDemo.javaAgent.MyJavaAgent",
      "Can-Redefine-Classes" -> "true",
      "Can-Retransform-Classes" -> "true"
    )
  )
