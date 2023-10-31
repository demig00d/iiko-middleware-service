val logbackVersion    = "1.4.11"
val sttpVersion       = "3.9.0"
val zioConfigVersion  = "3.0.7"
val zioLoggingVersion = "2.1.14"
val zioVersion        = "2.0.18"
val zioHttpVersion    = "3.0.0-RC2"
val calibanVersion    = "2.4.1"
val circeVersion      = "0.14.6"
val tapirVersion      = "1.8.3"
val quillVersion      = "4.8.0"
val postgresqlVersion = "42.5.4"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "2.13.12",
      )
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    name := "iiko-middleware-service",
    dockerBaseImage := "openjdk:11-jre-slim",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % sttpVersion,
      "com.softwaremill.sttp.client3" %% "circe"                         % sttpVersion,
      "com.softwaremill.sttp.client3" %% "core"                          % sttpVersion,
      "dev.zio"                       %% "zio"                           % zioVersion,
      // config
      "dev.zio" %% "zio-config"          % zioConfigVersion,
      "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
      // logging
      "ch.qos.logback"                 % "logback-classic"   % logbackVersion,
      "com.softwaremill.sttp.client3" %% "slf4j-backend"     % sttpVersion,
      "dev.zio"                       %% "zio-logging-slf4j" % zioLoggingVersion,
      // json
      "io.circe" %% "circe-generic" % circeVersion,
      // db
      "io.getquill"   %% "quill-jdbc-zio" % quillVersion,
      "org.postgresql" % "postgresql"     % postgresqlVersion,
      // graphql
      "com.github.ghostdogpr"       %% "caliban"          % calibanVersion,
      "com.github.ghostdogpr"       %% "caliban-zio-http" % calibanVersion,
      "dev.zio"                     %% "zio-http"         % zioHttpVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio"   % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
    ),
  )
  .enablePlugins(JavaAppPackaging)
