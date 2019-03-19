libraryDependencies ++= {
  val PLAY_SLICK_VERSION = "4.0.0"
  Seq(
    // jdbc, https://www.playframework.com/documentation/2.7.x/PlaySlickFAQ#A-binding-to-play.api.db.DBApi-was-already-configured
//    evolutions,
    "com.typesafe.play" %% "play-slick" % PLAY_SLICK_VERSION,
    "com.typesafe.play" %% "play-slick-evolutions" % PLAY_SLICK_VERSION,
    "mysql" % "mysql-connector-java" % "8.0.11"
  )
}
