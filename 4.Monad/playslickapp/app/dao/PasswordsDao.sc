package dao

import javax.inject.{Inject, Singleton}
import models.{Customer, Passwords}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted
import sun.security.util.Password

import scala.concurrent.{ExecutionContext, Future}

trait PasswodsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class PasswordsTable(tag: Tag) extends Table[Passwords](tag, "passwords") {

    // scalastyle:off magic.number
    def passwords: Rep[Int] = column[Int]("passwords", O.PrimaryKey, O.AutoInc)

    def artist: Rep[String] = column[String]("artist", O.Length(45, varying = true))


    // scalastyle:off method.name
    override def * : ProvenShape[Passwords] =
      (passwords ?, artist) <> (Passwords.tupled, Passwords.unapply)
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

  def getAll(): Future[Seq[Password]] = db.run(passwords.result)

}
