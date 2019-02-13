package models

case class Message(message: String)

case class Product(id: Option[Int],
                   sku: String,
                   name: String,
                   description: String)

case class Customer(customer: Option[Int], name: String, phone: String)

case class Artists(artist: Option[Int],
                   email: String,
                   number: String,
                   active: Boolean,
                   created: java.sql.Timestamp)

case class Logins(login: Option[Int], providerId: String, providerKey: String)

case class Passwords(login: Option[Int],
                     hasher: String,
                     password: String,
                     salt: String)
