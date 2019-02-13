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

  class LoginsTable(tag: Tag) extends Table[Logins](tag, "LOGINS") {

    // scalastyle:off magic.number
    def login: Rep[Int] = column[Int]("login", O.PrimaryKey, O.AutoInc)

    def providerId: Rep[String] =
      column[String]("providerId", O.Length(45, varying = true))

    def providerKey: Rep[String] =
      column[String]("providerKey", O.Length(45, varying = true))

    // scalastyle:off method.name
    override def * : ProvenShape[Logins] =
      (login ?, providerId, providerKey) <> (Logins.tupled, Logins.unapply)
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
