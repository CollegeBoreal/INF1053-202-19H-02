package daos

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.{Inject, Singleton}
import com.mohiva.play.silhouette.api.repositories.AuthenticatorRepository
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.Authenticator
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.duration.FiniteDuration._
import scala.concurrent.{ExecutionContext, Future}

trait AuthenticatorComponent {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._
  import slick.lifted.ProvenShape

  class AuthenticatorTable(tag: Tag)
      extends Table[Authenticator](tag, "AUTHENTICATORS") {

    // scalastyle:off magic.number
    def provider: Rep[Int] = column[Int]("provider", O.PrimaryKey)

    def key: Rep[String] =
      column[String]("key", O.Length(45, varying = true), O.PrimaryKey)

    def lastUsed: Rep[java.sql.Timestamp] =
      column[java.sql.Timestamp]("lastUsed")

    def expiration: Rep[java.sql.Timestamp] =
      column[java.sql.Timestamp]("expiration")

    def idleTimeOut: Rep[Int] = column[Int]("idleTimeOut")

    def duration: Rep[Int] = column[Int]("duration")

    def id: Rep[String] =
      column[String]("id", O.Length(300, varying = true))

    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Authenticator] =
      (provider, key, lastUsed, expiration, idleTimeOut, duration, id) <> (Authenticator.tupled, Authenticator.unapply)

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

  override def find(id: String): Future[Option[JWTAuthenticator]] = db.run {
    for {
      auth <- authenticators.filter(_.id === id).result.map(_.headOption)
    } yield
      auth match {
        case Some(x) =>
          Some(
            JWTAuthenticator(id,
                             LoginInfo("credentials", x.key),
                             new org.joda.time.DateTime(x.lastUsed),
                             new org.joda.time.DateTime(x.expiration),
                             None))
      }
  }

  override def add(authenticator: JWTAuthenticator): Future[JWTAuthenticator] =
    db.run {
        lazy val dt = new java.sql.Timestamp(
          java.util.Calendar.getInstance().getTimeInMillis)
        /*
                  authenticators
                    .filter(fields => fields.provider === 1 && fields.key === authenticator.loginInfo.providerKey)
                    .map(fields => (fields.lastUsed, fields.expiration))
                    .update((new java.sql.Timestamp(authenticator.lastUsedDateTime.getMillis), new java.sql.Timestamp(authenticator.expirationDateTime.getMillis)))
         */
        //            case (_, None) =>
        authenticators +=
          Authenticator(1,
                        authenticator.loginInfo.providerKey,
                        dt,
                        dt,
                        0,
                        32400,
                        authenticator.id)
      }
      .map(_ => authenticator)

  override def update(
      authenticator: JWTAuthenticator): Future[JWTAuthenticator] =
    db.run {
        authenticators
          .filter(fields =>
            fields.provider === 1 && fields.key === authenticator.loginInfo.providerKey)
          .map(fields => (fields.lastUsed, fields.expiration))
          .update(
            (new java.sql.Timestamp(authenticator.lastUsedDateTime.getMillis),
             new java.sql.Timestamp(
               authenticator.expirationDateTime.getMillis)))

      }
      .map(_ => authenticator)

  override def remove(id: String): Future[Unit] =
    db.run {
        authenticators
          .filter(_.id === id)
          .delete
      }
      .map(_ => ())

}
