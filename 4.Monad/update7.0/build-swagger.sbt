
swaggerDomainNameSpaces := Seq("models")

libraryDependencies ++= Seq(
  "org.webjars" % "swagger-ui" % "3.13.3",
  "com.typesafe.play" %% "play-json" % "2.7.1" // Needed for Swagger
)

// J2EE JAXB Related
/*
libraryDependencies ++= {
  val xmlBindVersion = "2.2.11"
  Seq(
    "javax.xml.bind" % "jaxb-api" % xmlBindVersion,
    "com.sun.xml.bind" % "jaxb-core" % xmlBindVersion,
    "com.sun.xml.bind" % "jaxb-impl" % xmlBindVersion
  )
}
*/
