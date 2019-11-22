name := "csv-io"
// organization := "bg.ontarget"

version := "0.0.1"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "2.4.4" % "provided",
    "org.apache.spark" %% "spark-sql" % "2.4.4" % "provided",
    "joda-time" % "joda-time" % "2.10"
)

assemblyJarName in assembly := "csv-io-assembly.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
