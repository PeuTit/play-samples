libraryDependencies += "com.h2database" % "h2" % "2.1.214"

// Database migration
// https://github.com/flyway/flyway-sbt
addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "9.15.2")

// Slick code generation
// https://github.com/tototoshi/sbt-slick-codegen
addSbtPlugin("com.github.tototoshi" % "sbt-slick-codegen" % "1.4.0")

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0-M3-SNAPSHOT")
