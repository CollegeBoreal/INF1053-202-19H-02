package daos

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import javax.inject.{Inject, Singleton}
import models.{Login, Password}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import scala.concurrent.{ExecutionContext, Future}

trait PasswordComponent {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._
  import slick.lifted.ProvenShape

  class PasswordTable(tag: Tag) extends Table[Password](tag, "PASSWORDS") {

    // scalastyle:off magic.number
    def key: Rep[String] =
      column[String]("key", O.Length(45, varying = true), O.PrimaryKey)

    def active: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("active")

    def password: Rep[String] = column[String]("password")

    def hasher: Rep[String] = column[String]("hasher")

    def salt: Rep[Option[String]] = column[Option[String]]("salt")

    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Password] =
      (key, active, password, hasher, salt) <> (Password.tupled, Password.unapply)

    // scalastyle:on method.name

  }

}

@Singleton
class PasswordDao @Inject()(
                             protected val dbConfigProvider: DatabaseConfigProvider,
                             loginDao: LoginDao)(implicit executionContext: ExecutionContext)
  extends DelegableAuthInfoDAO[PasswordInfo]
    with PasswordComponent
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val passwords = lifted.TableQuery[PasswordTable]

  def getAll: Future[Seq[Password]] = db.run(passwords.result)

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = db.run {
    for {
      Some(fields) <- passwords.filter(_.key === loginInfo.providerKey).result.map(_.headOption)
    } yield Some(PasswordInfo(fields.password, fields.hasher, fields.salt))
  }

  override def add(loginInfo: LoginInfo,
                   authInfo: PasswordInfo): Future[PasswordInfo] = {
    lazy val dt = new java.sql.Timestamp(java.util.Calendar.getInstance().getTimeInMillis)
    db.run {
      loginQuery(loginInfo).result >>
        (passwords += Password(loginInfo.providerKey, dt, authInfo.hasher, authInfo.password, authInfo.salt)) >>
        passwords.filter(_.key === loginInfo.providerKey).result
    }.map(_ => authInfo)
  }

  override def update(loginInfo: LoginInfo,
                      authInfo: PasswordInfo): Future[PasswordInfo] = save(loginInfo, authInfo)

  override def save(loginInfo: LoginInfo,
                    authInfo: PasswordInfo): Future[PasswordInfo] = {
    lazy val dt = new java.sql.Timestamp(java.util.Calendar.getInstance().getTimeInMillis)
    db.run {
      for (cs <- joinAction(loginInfo).map(_.head)) yield
        cs match {
        case (_, Some(oldAuthInfo)) =>
          passwords
            .filter(_.key === oldAuthInfo.key)
            .map(c => (c.hasher, c.password, c.salt))
            .update((authInfo.hasher, authInfo.password, authInfo.salt))
        case (_, None) =>
          passwords +=
            Password(loginInfo.providerKey, dt, authInfo.hasher, authInfo.password, authInfo.salt)
      }
    }.map(_ => authInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] =
    db.run {
      for { (_, Some(oldAuthInfo)) <- joinAction(loginInfo).map(_.head) } yield
        passwords.filter(_.key === oldAuthInfo.key).delete
    }.map(_ => ())

  private def loginQuery(loginInfo: LoginInfo): Query[loginDao.LoginTable, Login, Seq] =
    loginDao.logins.filter( fields => fields.provider === loginInfo.providerID.toInt && fields.key === loginInfo.providerKey)


  private def joinAction(loginInfo: LoginInfo): DBIO[Seq[(Login,Option[Password])]] =
    (loginQuery(loginInfo) joinLeft passwords on (_.key === _.key)).result

}
