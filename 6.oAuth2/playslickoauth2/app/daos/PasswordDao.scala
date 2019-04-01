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

trait PasswordComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
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

  def getAll(): Future[Seq[Password]] = db.run(passwords.result)

  private def loginInfoQuery(loginInfo: LoginInfo): Query[loginDao.LoginTable,loginDao.LoginTable#TableElementType,Seq] = {
    loginDao.logins.filter(a => a.provider === loginInfo.providerID.toInt && a.key === loginInfo.providerKey)
  }

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] =
   for {
      pwd <- db.run(passwords.filter(_.key === loginInfo.providerKey).result).map(_.headOption)
    } yield pwd match {
      case Some(y) => Some(PasswordInfo(y.password, y.hasher, y.salt))
    }

  override def add(loginInfo: LoginInfo,
                   authInfo: PasswordInfo): Future[PasswordInfo] = {
    val dt = new java.sql.Timestamp(java.util.Calendar.getInstance().getTimeInMillis)
    db.run {
      loginDao.logins.filter(x => x.provider === loginInfo.providerID.toInt && x.key === loginInfo.providerKey).result >>
        (passwords += Password(loginInfo.providerKey,dt,authInfo.hasher, authInfo.password, authInfo.salt)) >>
      passwords.filter(_.key === loginInfo.providerKey).result
    }.map(_ => authInfo)
  }

  override def update(loginInfo: LoginInfo,
                      authInfo: PasswordInfo): Future[PasswordInfo] = ???

  override def save(loginInfo: LoginInfo,
                    authInfo: PasswordInfo): Future[PasswordInfo] = ???

  override def remove(loginInfo: LoginInfo): Future[Unit] = ???
}
