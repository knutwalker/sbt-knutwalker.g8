import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbtrelease.ReleasePlugin.autoImport._
import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader.license._

import scala.language.implicitConversions

object Build extends AutoPlugin {
  override def trigger = allRequirements
  override def requires = JvmPlugin

  val scalacFlags = List(
    "-deprecation",
    "-explaintypes",
    "-feature",
    "-unchecked",
    "-encoding", "UTF-8",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Xlint:_",
    "-Xfuture",
    "-Xfatal-warnings",
    "-Yclosure-elim",
    "-Yconst-opt",
    "-Ydead-code",
    "-Yno-adapted-args",
    "-Ypatmat-exhaust-depth", "42",
    "-Ywarn-inaccessible",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-dead-code",
    "-Ywarn-infer-any",
    "-Ywarn-adapted-args",
    "-Ywarn-numeric-widen",
    // I found warn on unused things to be too aggressive
    // "-Ywarn-unused",
    // "-Ywarn-unused-import",
    // "-Ywarn-value-discard",
    // emit jdk8 bytecode and 2.12 forward compat
    "-target:jvm-1.8",
    // 2.12 forward compat - enables SAM support
    "-Xexperimental",
    // Emitting proper LMF requires GenBCode and method lambdafication.
    // Just make sure to use at least Scala 2.11.8, otherwise you will run into a very very nasty bug.
    // see https://github.com/scala/scala/pull/4588 and https://github.com/sbt/sbt/issues/2076
    "-Ybackend:GenBCode",
    "-Ydelambdafy:method"
  )

  object autoImport {
    lazy val RunDebug = config("debug") extend Runtime
    lazy val doNotPublish = List(
              publish := (),
         publishLocal := (),
      publishArtifact := false
    )
  }
  import autoImport.RunDebug

  override lazy val projectConfigurations =
    List(RunDebug)

  // We add the scala-java8-compat module as the last piece of config to make
  // SAM/LMF Bytecode support possible
  override lazy val projectSettings = List(
                 name := "$name$-" + thisProject.value.id,
         organization := "$organization$",
            startYear := Some(2017),
          description := "$name$",
         scalaVersion := V.scala,
          logBuffered := false,
          shellPrompt := configurePrompt,
       scalacOptions ++= scalacFlags,
            resolvers += Resolver.bintrayRepo("knutwalker", "maven"),
  libraryDependencies += "org.scala-lang.modules"   %% "scala-java8-compat" % V.java8comp,
  libraryDependencies += "com.google.code.findbugs"  % "jsr305"             % V.jsr305 % "provided",
    publishMavenStyle := true,
 pomIncludeRepository := { _ => false },
      publishArtifact in Test               := false,
        scalacOptions in (Compile, console) ~= (_ filterNot (x => x == "-Xfatal-warnings" || x.startsWith("-Ywarn"))),
        scalacOptions in (Test, console)    ~= (_ filterNot (x => x == "-Xfatal-warnings" || x.startsWith("-Ywarn"))),
        scalacOptions in Test               ~= (xs => xs.filterNot(x => x == "-Xfatal-warnings" || x.startsWith("-Ywarn")) :+ "-Yrangepos"),
      initialCommands in console            := "",
      initialCommands in consoleQuick       := "",
                 fork in run                := true,
         connectInput in run                := true,
          javaOptions in RunDebug          ++= List("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"),
                 fork in RunDebug           := true,
         connectInput in RunDebug           := true,
             homepage := Some(url("$projectUrl$")),
             licenses := List("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
              scmInfo := _scmInfo,
              headers := _headers(startYear.value),
    releaseTagComment := "Release version " + version.value,
 releaseCommitMessage := "Set version to " + version.value,
   releaseVersionBump := sbtrelease.Version.Bump.Minor,
       pomPostProcess := { n => removeScoverage.transform(n).head },
             pomExtra :=
               <developers>
                 <developer>
                   <id>$githubUser$</id>
                   <name>$licenseHolder$</name>
                   <url>$developerUrl$/</url>
                 </developer>
               </developers>
  ) ++ inConfig(RunDebug)(Defaults.compileSettings)

  lazy val configurePrompt = (st: State) => {
    import scala.Console._
    val name = Project.extract(st).currentRef.project
    val color = name match {
      case "bench" => MAGENTA
      case "tests" => GREEN
      case _       => CYAN
    }
    val prefix = if (name.endsWith("parent")) "" else "[" + color + name + RESET + "]"
    prefix + "> "
  }

  lazy val _scmInfo = Some(ScmInfo(
    url("$projectUrl$"),
    "scm:git:$projectUrl$.git",
    Some("scm:git:$projectUrl$.git")
  ))

  lazy val removeScoverage = new scala.xml.transform.RuleTransformer(NoScoverage)
  object NoScoverage extends scala.xml.transform.RewriteRule {
    override def transform(n: scala.xml.Node): scala.xml.NodeSeq =
      if (n.label == "dependency" && (n \ "groupId").text == "org.scoverage")
        scala.xml.NodeSeq.Empty
      else n
  }

  def _headers(sy: Option[Int]) = {
    val thisYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
    val years = List(sy.getOrElse(thisYear), thisYear).distinct.mkString(" – ")
    Map(  "java" -> Apache2_0(years, "$licenseHolder$"),
         "scala" -> Apache2_0(years, "$licenseHolder$"))
  }
}
