package models

case class Passwords(login: Option[Int],
                     hasher: String,
                     password: String,
                     salt: String)
