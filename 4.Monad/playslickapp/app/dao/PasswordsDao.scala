package dao

import javax.inject.{Inject, Singleton}
import models.Passwords
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.{ExecutionContext, Future}

trait PasswordsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class PasswordsTable(tag: Tag) extends Table[Passwords](tag, "PASSWORDS") {

    // scalastyle:off magic.number
    def login: Rep[Int] = column[Int]("login", O.PrimaryKey, O.AutoInc)

    def hasher: Rep[String] = column[String]("hasher")
    def password: Rep[String] = column[String]("password")
    def salt: Rep[String] = column[String]("salt")
    // scalastyle:off method.name
    override def * : ProvenShape[Passwords] =
      (login ?, hasher, password, salt) <> (Passwords.tupled, Passwords.unapply)
    // scalastyle:on method.name

  }

}

@Singleton
class PasswordsDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends PasswordsComponent
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val passwords = lifted.TableQuery[PasswordsTable]

  def getAll(): Future[Seq[Passwords]] = db.run(passwords.result)

}
