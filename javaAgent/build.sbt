lazy val agent = (project in file("."))
  .settings(
    name := "javaAgent",
    version := "0.1",
    scalaVersion := "2.12.8",
    organization := "Yoppworks Inc.",
    libraryDependencies += "org.javassist" % "javassist" % "3.24.0-GA",
    assemblyJarName in assembly := "demo-agent.jar",
    packageOptions += Package.ManifestAttributes(
      "Premain-Class" -> "com.yoppworks.internal.instrumentationDemo.javaAgent.MyJavaAgent",
      "Can-Redefine-Classes" -> "true",
      "Can-Retransform-Classes" -> "true")
  )