package models

case class Authenticator(provider: Int,
                         key: String,
                         lastUsed: java.sql.Timestamp,
                         expiration: java.sql.Timestamp,
                         idleTimeOut: Int,
                         duration: Int,
                         id: String)
