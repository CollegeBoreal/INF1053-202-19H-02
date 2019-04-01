package daos

import javax.inject.{Inject, Singleton}

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.{ExecutionContext, Future}

trait UserComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class UserTable(tag: Tag) extends Table[User](tag, "USERS") {

    // scalastyle:off magic.number
    def user: Rep[Int] = column[Int]("user", O.PrimaryKey, O.AutoInc)

    def key: Rep[String] =
      column[String]("key", O.Length(45, varying = true))

    def active: Rep[Boolean] = column[Boolean]("active")

    def created: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created")

    // scalastyle:off method.name
    override def * : ProvenShape[User] =
      (user ?, key, active, created) <> (User.tupled, User.unapply)
    // scalastyle:on method.name

  }

}

@Singleton
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends UserComponent
    with IdentityService[User]
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val users = lifted.TableQuery[UserTable]

  def getAll: Future[Seq[User]] = db.run(users.result)

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] =
    db.run(users.filter(_.key === loginInfo.providerKey).result).map(_.headOption)

}
