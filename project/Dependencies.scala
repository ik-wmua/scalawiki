import sbt._

object Dependencies {

  val AaltoXmlV = "1.0.0"
  val BetterFilesAkkaV = "3.4.0"
  val BlameApiV = "6.4.1"
  val ChronicleMapV = "3.14.5"
  val ChronoScalaV = "0.1.5"
  val CommandLineArgumentsV = "0.5.0"
  val FicusV = "1.4.3"
  val GuavaV = "20.0"
  val H2V = "1.4.189"
  val JimFsV = "1.1"
  val JodaTimeV = "2.9.9"
  val JSoupV = "1.8.3"
  val LogbackClassicV = "1.2.3"
  val MockServerV = "5.3.0"
  val ReactiveStreamsV = "1.0.2"
  val RetryV = "0.3.0"
  val Scala212V = "2.12.6"
  val Scala211V = "2.11.12"
  val ScalaChartV = "0.5.1"
  val ScalaCheckV = "1.14.0"
  val ScalaCsvV = "1.3.4"
  val ScalaXmlV = "1.0.6"
  val Slf4jV = "1.7.25"
  val SwcEngineV = "3.1.7"
  val TypesafeConfigV = "1.3.2"

  val resolvers = Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
    Resolver.jcenterRepo,
    Resolver.bintrayRepo("rick-beton", "maven"),
    Resolver.bintrayRepo("softprops", "maven")
  )

  val overrides = Seq(
    Library.Akka.actor,
    "org.reactivestreams" % "reactive-streams" % ReactiveStreamsV,
    "org.scala-lang.modules" %% "scala-xml" % ScalaXmlV,
    "com.google.guava" % "guava" % GuavaV,
    Library.Commons.codec,
    Library.Commons.io,
    Library.Commons.lang,
    "com.typesafe" % "config" % TypesafeConfigV,
    Library.Poi.ooxml,
    Library.Jackson.core,
    Library.Jackson.annotations,
    Library.Jackson.databind,
    "joda-time" % "joda-time" % JodaTimeV,
    "org.slf4j" % "slf4j-api" % Slf4jV,
    "ch.qos.logback" % "logback-classic" % LogbackClassicV
  )

  object Library {

    val commandLineArguments = "com.concurrentthought.cla" %% "command-line-arguments" % CommandLineArgumentsV
    val scalacheck = "org.scalacheck" %% "scalacheck" % ScalaCheckV

    object Akka {
      val AkkaV = "2.5.12"
      val AkkaHttpV = "10.1.1"

      val actor = "com.typesafe.akka" %% "akka-actor" % AkkaV
      val stream = "com.typesafe.akka" %% "akka-stream" % AkkaV
      val http = "com.typesafe.akka" %% "akka-http" % AkkaHttpV
      val httpCaching = "com.typesafe.akka" %% "akka-http-caching" % AkkaHttpV
    }

    object Commons {
      val CommonsCodecV = "1.10"
      val CommonsCompressV = "1.9"
      val CommonsLang3V = "3.7"
      val CommonsIoV = "2.6"

      val codec = "commons-codec" % "commons-codec" % CommonsCodecV
      val io = "commons-io" % "commons-io" % CommonsIoV
      val lang = "org.apache.commons" % "commons-lang3" % CommonsLang3V
      val compress = "org.apache.commons" % "commons-compress" % CommonsCompressV
    }

    object Jackson {
      val JacksonV = "2.9.2"

      val core = "com.fasterxml.jackson.core" % "jackson-core" % JacksonV
      val annotations = "com.fasterxml.jackson.core" % "jackson-annotations" % JacksonV
      val databind = "com.fasterxml.jackson.core" % "jackson-databind" % JacksonV
    }

    object Play {
      val PlayJsonV = "2.6.9"
      val TwirlV = "1.3.13"

      val json = "com.typesafe.play" %% "play-json" % PlayJsonV
      val twirlApi = "com.typesafe.play" %% "twirl-api" % TwirlV
    }

    object Poi {
      val PoiV = "3.13"
      val PoiXwpfV = "1.0.6"

      val scratchpad = "org.apache.poi" % "poi-scratchpad" % PoiV
      val ooxml = "org.apache.poi" % "poi-ooxml" % PoiV
      val converter = "fr.opensagres.xdocreport" % "org.apache.poi.xwpf.converter.xhtml" % PoiXwpfV
    }

    object Slick {
      val SlickV = "3.2.3"

      val slick = "com.typesafe.slick" %% "slick" % SlickV
      val hikaricp = "com.typesafe.slick" %% "slick-hikaricp" % SlickV
    }

    object Specs2 {
      val SpecsV = "4.2.0"

      val core = "org.specs2" %% "specs2-core" % SpecsV
      val matcherExtra = "org.specs2" %% "specs2-matcher-extra" % SpecsV
      val mock = "org.specs2" %% "specs2-mock" % SpecsV
    }
  }
}