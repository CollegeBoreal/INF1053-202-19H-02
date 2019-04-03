import javax.inject.{Inject, Singleton}
import models.{Customer, Product}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait CustomersComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class CustomerTable(tag: Tag) extends Table[Customer](tag, "customers") {
    def customer: Rep[Int] = column[Int]("customer", O.PrimaryKey, O.AutoInc)
    def name: Rep[String] =
      column[String]("name", O.Length(127, varying = true))
    def phone: Rep[String] =
      column[String]("phone", O.Length(511, varying = true))

    override def * : ProvenShape[Customer] =
      (customer.?, name, phone) <> (Customer.tupled, Customer.unapply)

  }
}

@Singleton()
class CustomersDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends CustomersComponent
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val customers = TableQuery[CustomerTable]

  def getAll: Future[Seq[Customer]] = db.run(customers.result)
}
