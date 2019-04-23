package daos.authenticator

import java.time.LocalDateTime

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.{Inject, Singleton}
import com.mohiva.play.silhouette.api.repositories.AuthenticatorRepository
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.Authenticator
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthenticatorDAO @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends AuthenticatorDTO
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
//        case _ => None
      }
  }

  override def add(
      authenticator: JWTAuthenticator): Future[JWTAuthenticator] = {
    val exists =
      authenticators
        .filter(fields =>
          fields.provider === 1 && fields.key === authenticator.loginInfo.providerKey)
        .exists
    db.run {
        lazy val current = java.util.Calendar.getInstance().getTimeInMillis
        lazy val lastUsed = new LocalDateTime(current)
        lazy val expiration = new LocalDateTime(current + (12 * 3600000))
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
                        lastUsed,
                        expiration,
                        0,
                        32400,
                        authenticator.id)
      }
      .map(_ => authenticator)
  }

  override def update(
      authenticator: JWTAuthenticator): Future[JWTAuthenticator] =
    db.run {
        lazy val current = java.util.Calendar.getInstance().getTimeInMillis
        lazy val lastUsed = new java.sql.Timestamp(current)
        lazy val expiration = new java.sql.Timestamp(current + (12 * 3600000))

        authenticators
          .filter(fields =>
            fields.provider === 1 && fields.key === authenticator.loginInfo.providerKey)
          .map(fields => (fields.lastUsed, fields.expiration))
          .update((lastUsed, expiration))
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
