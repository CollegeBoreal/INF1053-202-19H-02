package daos

import javax.inject.{Inject, Singleton}
import models.Login
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

trait LoginComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class LoginTable(tag: Tag) extends Table[Login](tag, "LOGINS") {

    // scalastyle:off magic.number
    def provider: Rep[Int] = column[Int]("provider")

    def key: Rep[String] =
      column[String]("key", O.Length(45, varying = true), O.PrimaryKey)
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Login] =
      (provider, key) <> (Login.tupled, Login.unapply)
    // scalastyle:on method.name

  }

}

@Singleton
class LoginDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends LoginComponent
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val logins = lifted.TableQuery[LoginTable]

  def getAll(): Future[Seq[Login]] = db.run(logins.result)

}
