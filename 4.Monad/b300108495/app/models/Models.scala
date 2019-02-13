package models

import java.sql.Timestamp

case class Message(message: String)

case class Product(id: Option[Int],
                   sku: String,
                   name: String,
                   description: String)

case class Customer(customer: Option[Int], name: String, phone: String)

case class Member(band: Option[Int], artist: Int, since: Timestamp)

case class Band(band: Option[Int], name: String, description: String)
