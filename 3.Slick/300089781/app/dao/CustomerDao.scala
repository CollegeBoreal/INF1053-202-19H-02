package dao

import javax.inject.{Inject, Singleton}
import models.{Customer, Product}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait ProductsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ProductTable(tag: Tag) extends Table[Product](tag, "products") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    // scalastyle:off magic.number
    def sku: Rep[String] = column[String]("sku", O.Length(31, varying = true))
    def name: Rep[String] =
      column[String]("name", O.Length(127, varying = true))
    def description: Rep[String] =
      column[String]("description", O.Length(511, varying = true))
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Product] =
      (id.?, sku, name, description) <> (Product.tupled, Product.unapply)
    // scalastyle: on method.name
  }
}

@Singleton()
class CustomerDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends Customer
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val products = TableQuery[Customer]

  def getAll: Future[Seq[Product]] = db.run(customer)
}
