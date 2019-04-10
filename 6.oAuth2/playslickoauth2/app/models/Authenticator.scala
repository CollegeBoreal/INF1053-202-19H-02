package models

case class Authenticator(provider: Int,
                         key: String,
                         lastUsed: java.time.LocalDateTime,
                         expiration: java.time.LocalDateTime,
                         idleTimeOut: Int,
                         duration: Int,
                         id: String)
