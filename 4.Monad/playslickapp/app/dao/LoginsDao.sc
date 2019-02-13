package dao

import javax.inject.{Inject, Singleton}
import models.{Customer, Logins}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

trait LoginsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class LoginsTable(tag: Tag) extends Table[Logins](tag, "logins") {

    // scalastyle:off magic.number
    def logins: Rep[Int] = column[Int]("logins", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name", O.Length(45, varying = true))

    def phone: Rep[String] =
      column[String]("phone", O.Length(45, varying = true))
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Logins] =
      (logins?, name, phone) <> (Logins.tupled, Logins.unapply)
    // scalastyle:on method.name

  }

}

@Singleton
class LoginsDao @Inject()(
                              protected val dbConfigProvider: DatabaseConfigProvider)(
                              implicit executionContext: ExecutionContext)
  extends LoginsComponent
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val logins = lifted.TableQuery[LoginsTable]

  def getAll(): Future[Seq[Logins]] = db.run(logins.result)

}
