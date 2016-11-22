name := """notifications"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "com.rabbitmq" % "amqp-client" % "3.6.5",
  "mysql" % "mysql-connector-java" % "5.1.36"
  )
  
PlayKeys.externalizeResources := false

fork in run := true