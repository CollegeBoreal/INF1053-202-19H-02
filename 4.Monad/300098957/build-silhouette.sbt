// Silhouette related library
resolvers += Resolver.jcenterRepo

// Pages Security Related
libraryDependencies ++= {
  val silhouetteVersion = "5.0.7"
  Seq(
    "com.mohiva" %% "play-silhouette"                    % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-password-bcrypt"    % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-persistence"        % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-crypto-jca"         % silhouetteVersion,
    "com.mohiva" %% "play-silhouette-testkit"            % silhouetteVersion % Test,
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.iheart" %% "ficus" % "1.4.3",
    ehcache,
    guice,
    filters
  )
}

