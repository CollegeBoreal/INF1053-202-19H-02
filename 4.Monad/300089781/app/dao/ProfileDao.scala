package dao

import javax.inject.{Inject, Singleton}
import models.{Band, Profile}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

trait ProfileEditorComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ProfileTable(tag: Tag) extends Table[Profile](tag, "PROFILES") {
    def profile: Rep[Int] = column[Int]("profile", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] =
      column[String]("firstname", O.Length(127, varying = true))
    def lastname: Rep[String] =
      column[String]("lastname", O.Length(511, varying = true))
    // scalastyle:on magic.number
    def address: Rep[String] =
      column[String]("address", O.Length(511, varying = true))
    def street: Rep[String] =
      column[String]("street", O.Length(511, varying = true))
    def city: Rep[String] =
      column[String]("city", O.Length(511, varying = true))
    def state: Rep[String] =
      column[String]("state", O.Length(511, varying = true))
    def zip: Rep[String] =
      column[String]("zip", O.Length(511, varying = true))

    // scalastyle:off method.name
    override def * : ProvenShape[Profile] =
      (profile.?, name, lastname, address, street, city, state, zip) <> (Profile.tupled, Profile.unapply)
    // scalastyle: on method.name
  }
}

@Singleton()
class ProfileDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends ProfileEditorComponent
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val profiles = lifted.TableQuery[ProfileTable]

  def getAll: Future[Seq[Profile]] = db.run(profiles.result)

  def add(profile: Profile) = db.run(profiles += profile)
}
