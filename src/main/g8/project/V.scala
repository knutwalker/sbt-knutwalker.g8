import sbt._

//@formatter:off
object V {
  val     agrona = "$agronaVersion$"
  val       akka = "$akkaVersion$"
  val   akkaHttp = "$akkaHttpVersion$"
  val      circe = "$circeVersion$"
  val      fansi = "$fansiVersion$"
  val  java8comp = "$java8compVersion$"
  val      jline = "$jlineVersion$"
  val     jsr305 = "$jsr305Version$"
  val     lucene = "$luceneVersion$"
  val      scala = "2.11.8"
  val scalacheck = "1.13.4"
  val  shapeless = "$shapelessVersion$"
  val     specs2 = "3.8.6"
  val transducer = "$transducerVersion$"
  val      typed = "2.0.0-a24-5dd0deaad210082d3147ab1beeee37bc585d2305"
  val validation = "$validationVersion$"
}

object L {
  val api = Seq(
    "com.typesafe.akka" %% "akka-actor"         % V.akka
  , "com.chuusai"       %% "shapeless"          % V.shapeless
  , "de.knutwalker"     %% "transducers-scala"  % V.transducer
  , "de.knutwalker"     %% "typed-actors"       % V.typed
  , "de.knutwalker"     %% "validation"         % V.validation
  , "org.agrona"         % "agrona"             % V.agrona
  , "org.apache.lucene"  % "lucene-core"        % V.lucene
  , "org.scala-lang"     % "scala-compiler"     % V.scala % "provided"
  )

  val core = Seq(
    "com.typesafe.akka" %% "akka-slf4j"                 % V.akka
  , "org.apache.lucene"  % "lucene-analyzers-common"    % V.lucene
  , "org.apache.lucene"  % "lucene-analyzers-icu"       % V.lucene
  , "org.apache.lucene"  % "lucene-analyzers-phonetic"  % V.lucene
  , "org.apache.lucene"  % "lucene-backward-codecs"     % V.lucene
  , "org.apache.lucene"  % "lucene-classification"      % V.lucene
  , "org.apache.lucene"  % "lucene-codecs"              % V.lucene
  , "org.apache.lucene"  % "lucene-expressions"         % V.lucene
  , "org.apache.lucene"  % "lucene-facet"               % V.lucene
  , "org.apache.lucene"  % "lucene-grouping"            % V.lucene
  , "org.apache.lucene"  % "lucene-highlighter"         % V.lucene
  , "org.apache.lucene"  % "lucene-join"                % V.lucene
  , "org.apache.lucene"  % "lucene-memory"              % V.lucene
  , "org.apache.lucene"  % "lucene-misc"                % V.lucene
  , "org.apache.lucene"  % "lucene-queries"             % V.lucene
  , "org.apache.lucene"  % "lucene-queryparser"         % V.lucene
  , "org.apache.lucene"  % "lucene-sandbox"             % V.lucene
  , "org.apache.lucene"  % "lucene-spatial"             % V.lucene
  , "org.apache.lucene"  % "lucene-spatial-extras"      % V.lucene
  , "org.apache.lucene"  % "lucene-spatial3d"           % V.lucene
  , "org.apache.lucene"  % "lucene-suggest"             % V.lucene
  )

  val http = Seq(
    "com.typesafe.akka" %% "akka-http-core"       % V.akkaHttp
  , "com.typesafe.akka" %% "akka-http"            % V.akkaHttp
  , "com.typesafe.akka" %% "akka-http-spray-json" % V.akkaHttp
  , "io.circe"          %% "circe-core"           % V.circe
  , "io.circe"          %% "circe-generic"        % V.circe
  , "io.circe"          %% "circe-generic-extras" % V.circe
  , "io.circe"          %% "circe-parser"         % V.circe
  )

  val cli = Seq(
    "com.lihaoyi"    %% "fansi"         % V.fansi
  , "jline"           % "jline"         % V.jline
  , "org.scala-lang"  % "scala-reflect" % V.scala
  )

  val tests = Seq(
    "com.typesafe.akka" %% "akka-http-testkit"       % V.akkaHttp    % "test"
  , "com.typesafe.akka" %% "akka-multi-node-testkit" % V.akka        % "test"
  , "com.typesafe.akka" %% "akka-stream-testkit"     % V.akka        % "test"
  , "com.typesafe.akka" %% "akka-testkit"            % V.akka        % "test"
  , "org.apache.lucene"  % "lucene-test-framework"   % V.lucene      % "test"
  , "org.scalacheck"    %% "scalacheck"              % V.scalacheck  % "test"
  , "org.specs2"        %% "specs2-core"             % V.specs2      % "test"
  , "org.specs2"        %% "specs2-matcher-extra"    % V.specs2      % "test"
  , "org.specs2"        %% "specs2-scalacheck"       % V.specs2      % "test"
  )
}
//@formatter:on
