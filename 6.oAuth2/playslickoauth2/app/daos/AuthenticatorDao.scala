package daos

import javax.inject.{Inject, Singleton}
import com.mohiva.play.silhouette.api.repositories.AuthenticatorRepository
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.Authenticator
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.{ExecutionContext, Future}

trait AuthenticatorComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class AuthenticatorTable(tag: Tag)
      extends Table[Authenticator](tag, "AUTHENTICATORS") {

    // scalastyle:off magic.number
    def provider: Rep[Int] = column[Int]("user", O.PrimaryKey)

    def key: Rep[String] =
      column[String]("key", O.Length(45, varying = true), O.PrimaryKey)

    def lastUsed: Rep[java.sql.Timestamp] =
      column[java.sql.Timestamp]("lastUsed")

    def expiration: Rep[java.sql.Timestamp] =
      column[java.sql.Timestamp]("expiration")

    def idleTimeOut: Rep[Int] = column[Int]("idleTimeOut")

    def duration: Rep[Int] = column[Int]("duration")
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Authenticator] =
      (provider, key, lastUsed, expiration, idleTimeOut, duration) <> (Authenticator.tupled, Authenticator.unapply)
    // scalastyle:on method.name

  }

}

@Singleton
class AuthenticatorDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends AuthenticatorComponent
    with HasDatabaseConfigProvider[JdbcProfile]
    with AuthenticatorRepository[JWTAuthenticator] {

  import profile.api._

  val authenticators = lifted.TableQuery[AuthenticatorTable]

  override def find(id: String): Future[Option[JWTAuthenticator]] = ???

  override def add(authenticator: JWTAuthenticator): Future[JWTAuthenticator] =
    ???

  override def update(
      authenticator: JWTAuthenticator): Future[JWTAuthenticator] = ???

  override def remove(id: String): Future[Unit] = ???

}
