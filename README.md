# Project Structure

├── build.sbt
├── project
│   ├── assembly.sbt
│   └── build.properties
├── README.md
└── src
    └── main
        └── scala
            └── bg                  /*              */
                └── ontarget        /* package name */
                    └── sparkcourse /*              */
                        └── WordCount.scala


# Environment

* Make sure to install JDK 1.8 (and put bin folder in PATH)
* Make sure to install Python2.7 and put it's root and Script folders in PATH

# Configuration files

## build.sbt

    // Project name and version
    name := "wordcount"
    version := "0.0.1"

    scalaVersion := "2.11.8"

    libraryDependencies ++= Seq(
        // core-spark -- provided means not to include it in fat jar
        "org.apache.spark" %% "spark-core" % "2.4.4" % "provided",
        // Add any other dependencies here -- they'll be included in fat jar
        "joda-time" % "joda-time" % "2.10"
    )

    // How is the fat jar named (under target/scala-2.11/)
    assemblyJarName in assembly := "wordcount-assembly.jar"

    // Do not include the scala classes insite the fat jar
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

## project/assembly.sbt

    // This is a plugin for sbt that makes the fat jar
    // The fat jar includes project + all dependencies in one package,
    // so it is easier to distribute
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")

# Configuring IntelliJ Idea

* Open a project (first time)

    (menu) File -> New -> Import Project...

    then select the directory of the project

    Import Project from External Model -> sbt

    Make sure to choose JDK 1.8 from the appropriate dropdown

* To clean up all idea-related files

    (menu) File -> Close Project, close the IDE
    remove the .idea folder under the project root

    Add project again

* To configure sbt assembly

    (menu) Run -> Edit Configurations... -> top-left plus (+) icon -> sbt Task

    under Name type 'Make Jar' (or whatever)
    under Tasks type 'clean assembly'

* To build the project (checks for errors)

    Green Hammer icon (toolbar) OR (menu) Build -> Make Project

* To build the Jar:

    select Make Jar from the dropdown next to the Green Hammer (toolbar), then click the
    green arrow (toolbar)

    OR

    (menu) Run -> Run... -> select 'Make Jar' from the dialog

# Configuring sbt for command line use

* Download sbt, extract, add path\to\sbt\bin to PATH (check next section for
  details on how)
* navigate to project directory, execute `sbt clean assembly` to make the jar
* NOTE: `sbt clean package` will make a jar file with only project classes,
  whereas `sbt clean assembly` will put all external dependencies inside, if
  you have any external dependencies, you should use `sbt clean assembly`.
* (optionally) Clean the sbt jar cache (all downloaded dependencies) by wiping
  `C:\Users\YOURNAME\.sbt`

# running Spark from command line

* Make sure to add spark/bin folder to PATH

    Windows Key -> (type) Environment Variables -> choose Edit the System
    Environment Variables -> (button) Environment Variables...

    OR

    in cmd shell execute:

    set PATH=C:\full\path\to\spark\bin;%PATH%

    NOTE: You can put all environment variables in one bat file and then `call
    path\to\batfile.bat` from cmd, to get them in scope

* Configure hadoop conf & winutils

    * Make a folder for hadoop: C:\hadoop
    * Make folders bin and conf inside: C:\hadoop\bin, C:\hadoop\conf
    * Download https://github.com/steveloughran/winutils or
    https://github.com/cdarlint/winutils (depending on hadoop version)
    * move the content of winutils\hadoop-2.7.1\bin\\\* to C:\hadoop\bin
    * copy hadoop configuration (/etc/hadoop/conf/\*) from running cluster in

    * set `HADOOP_HOME` environment variable to C:\hadoop
    * set `HADOOP_CONF_DIR` environment variable to C:\hadoop\conf
    * add C:\hadoop\bin to PATH: set PATH=C:\hadoop\bin;%PATH% (or via UI)
    * (optionally) set `HADOOP_USER_NAME` to your username inside HDFS

* To run the application locally

    spark-submit --classes full.class.name.MyClass path\to\assembly.jar

* To run the application in the Hadoop cluster

    spark-submit --master yarn --deploy-mode cluster --classes full.class.name.MyClass path\to\assembly.jar
