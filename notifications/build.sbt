name := """notifications"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)

libraryDependencies += "com.rabbitmq" % "amqp-client" % "3.6.5"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.29"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.9"