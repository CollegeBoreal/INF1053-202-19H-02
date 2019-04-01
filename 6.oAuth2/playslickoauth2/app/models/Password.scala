package models

case class Password(key: String,
                    active: java.sql.Timestamp,
                    secret: String,
                    hasher: String,
                    salt: Option[String] = None)
