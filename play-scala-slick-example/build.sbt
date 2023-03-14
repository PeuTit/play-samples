lazy val scala213 = "2.13.10"
lazy val scala3 = "3.3.0-RC3"

lazy val root = (project in file("."))
  .settings(
    name := "play-scala-slick-examples",
    version := "1.0-SNAPSHOT",
    scalaVersion := scala3,
    crossScalaVersions := Seq(scala213, scala3),
  )
  .aggregate(
    basicSample,
    computerDatabaseSample,
    personSample,
  )

def sampleProject(name: String) =
  Project(s"$name-sample", file("samples") / name)
    .enablePlugins(PlayScala)
    .settings(
      scalaVersion := scala3,
      crossScalaVersions := Seq(scala213, scala3),
      scalacOptions ++= Seq(
        "-feature",
        "-Xfatal-warnings",
      ),
      scalacOptions ++= {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((2, _)) =>
            Seq(
              "-Xsource:3",
            )
          case Some((3, _)) =>
            Seq(
              "-explain",
            )
          case _ => Nil
        }
      },
      libraryDependencies ++= Seq(
        guice,
        "com.h2database" % "h2" % "2.1.214",
        "com.typesafe.play" %% "play-slick" % "5.1.0+57-1a617966+20230315-1245-SNAPSHOT",
        "com.typesafe.play" %% "play-slick-evolutions" % "5.1.0+57-1a617966+20230315-1245-SNAPSHOT",
        specs2 % Test,
      ),
      excludeDependencies ++= {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((3, _)) =>
            Seq(
              ExclusionRule("com.typesafe.play", "ssl-config-core_2.13"),
              ExclusionRule("com.typesafe.play", "play_2.13"),
            )
          case _ => Nil
        }
      },
      (Global / concurrentRestrictions) += Tags.limit(Tags.Test, 1)
    )
    .settings((Test / javaOptions) += "-Dslick.dbs.default.connectionTimeout=30 seconds")
    // We use a slightly different database URL for running the slick applications and testing the slick applications.
    .settings((Test / javaOptions) ++= Seq("-Dconfig.file=conf/test.conf"))

lazy val computerDatabaseSample = sampleProject("computer-database")

lazy val basicSample = sampleProject("basic")

lazy val personSample = sampleProject("person")
