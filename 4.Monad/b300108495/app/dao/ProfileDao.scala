
package dao

import javax.inject.{Inject, Singleton}
import models.Band
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait  ProfileEditorComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ProfileTable(tag: Tag) extends Table[Profile](tag, "profile") {
    def profile: Rep[Int] = column[Int]("profile", O.PrimaryKey, O.AutoInc)

    // scalastyle:off magic.number
    def firstname: Rep[String] =
      column[String]("firstname", O.Length(127, varying = true))
    def lastname: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))
    // scalastyle:on magic.number
    def address: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))
    def street: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))
    def city: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))
    def state: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))
    def zip: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))

    // scalastyle:off method.name
    override def * : ProvenShape[Profile] =
      (profile.?, firstname, lastname) <> (Profile.tupled, Profile.unapply)
    // scalastyle: on method.name
  }
}

@Singleton()
class BandsDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit executionContext: ExecutionContext)
  extends BandsComponent
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val bands = TableQuery[BandTable]

  def getAll: Future[Seq[Band]] = db.run(bands.result)
  def add(band: Band): Future[Int] = db.run(bands += band)
}
