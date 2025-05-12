scalaVersion := "3.7.0"
scalacOptions ++= Seq(
  "-source",
  "future",
  "-Xkind-projector:underscores",
  "-deprecation",
  "-experimental",
  "-Yexplicit-nulls"
)
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
