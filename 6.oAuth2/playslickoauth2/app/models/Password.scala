package models

case class Password(providerKey: String = "",
                    secret: String,
                    hasher: String,
                    salt: Option[String] = None)
