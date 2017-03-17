lazy val root = (project in file(".")).
  aggregate(app).
  settings(inThisBuild(List(
      organization := "com.github.3tty0n",
      scalaVersion := "2.12.1"
    )),
    name := "church-scala-root"
  )

lazy val app = (project in file("app")).
  settings(
    name := "church-scala",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "org.scalaz" %% "scalaz-core" % "7.2.10"
    )
  )
