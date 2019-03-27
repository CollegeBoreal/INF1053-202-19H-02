set databaseName=play-silhouette-rest-mongo
set hostAddress="localhost"
set portNumber="27017"
set scriptsFolder=%CD%/scripts

mongo "mongodb://%hostAddress%:%portNumber%/%databaseName%" --eval "db.dropDatabase()"
mongo "mongodb://%hostAddress%:%portNumber%/%databaseName%" %scriptsFolder%/createSchema.js
mongoimport --db %databaseName% -h %hostAddress%:%portNumber% --collection users --file %scriptsFolder%/users.json --jsonArray
mongoimport --db %databaseName% -h %hostAddress%:%portNumber% --collection passwords --file %scriptsFolder%/passwords.json --jsonArray
@timeout 3
