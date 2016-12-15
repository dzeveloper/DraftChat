import com.mojolly.scalate.ScalatePlugin.ScalateKeys._
import com.mojolly.scalate.ScalatePlugin._
import org.scalatra.sbt._

val ScalatraVersion = "2.5.0"

val JettyVersion = "9.3.14.v20161028"

ScalatraPlugin.scalatraSettings

scalateSettings

organization := "com.github.Kanris826"

name := "DraftChat"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-auth" % ScalatraVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.zaxxer" % "HikariCP" % "2.5.1",
  "org.scalatest" % "scalatest_2.11" % "3.0.1",
  "org.eclipse.jetty" % "jetty-server" % JettyVersion,
  "org.eclipse.jetty" % "jetty-servlet" % JettyVersion,
  "org.eclipse.jetty" % "jetty-webapp" % JettyVersion,
  "io.spray" % "spray-json_2.11" % "1.3.2",
  "net.liftweb" % "lift-webkit_2.10" % "2.6.3"
)

scalateTemplateConfig in Compile := {
  val base = (sourceDirectory in Compile).value
  Seq(
    TemplateConfig(
      base / "webapp" / "WEB-INF" / "templates",
      Seq.empty, /* default imports should be added here */
      Seq(
        Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
      ), /* add extra bindings here */
      Some("templates")
    )
  )
}

enablePlugins(JettyPlugin)

val runJetty = TaskKey[Unit]("runJetty", "Run Jetty")

fullRunTask(runJetty, Compile, "com.DraftChat.JettyLauncher")

val initDB = TaskKey[Unit]("initDB", "Init DB")

fullRunTask(initDB, Compile, "com.DraftChat.Init")