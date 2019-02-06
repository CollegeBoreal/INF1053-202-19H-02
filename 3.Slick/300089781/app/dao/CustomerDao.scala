package dao

import javax.inject.{Inject, Singleton}
import models.{Customer, Product}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile


import scala.concurrent.{ExecutionContext, Future}



trait CustomerComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape


  class CustomerTable (tag: Tag) extends Table[Customer](tag, "customers"){

    def customer: Rep[Int] = column[Int]("customer", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name",O.Length(45, varying = true))

    def phone: Rep[String] = column[String]("phone",O.Length(45, varying = true))

    override def * : ProvenShape[Customer] =
      (customer?, name, phone) <> (Customer.tupled, Customer.unapply)
  }
}

@Singleton
class CustomerDao @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends CustomerComponent
with HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  val customers= TableQuery[CustomerTable]
  def getAll: Future [Seq[Customer]] = db.run(customers.result)
}