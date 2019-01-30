package models

case class Message(message: String)
case class Customer(customer: Option[Int], name: String)

case class Product(id: Option[Int],
                   sku: String,
                   name: String,
                   description: String)
