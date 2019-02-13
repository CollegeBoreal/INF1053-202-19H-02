package dao

import javax.inject.{Inject, Singleton}
import models.Artists
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted
import scala.concurrent.{ExecutionContext, Future}

trait ArtistsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ArtistsTable(tag: Tag) extends Table[Artists](tag, "ARTISTS") {

    // scalastyle:off magic.number
    def artist: Rep[Int] = column[Int]("artist", O.PrimaryKey, O.AutoInc)

    def number: Rep[String] =
      column[String]("number", O.Length(45, varying = true))

    def email: Rep[String] =
      column[String]("email", O.Length(45, varying = true))

    def active: Rep[Boolean] = column[Boolean]("active")

    def created: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created")

    // scalastyle:off method.name
    override def * : ProvenShape[Artists] =
      (artist ?, number, email, active, created) <> (Artists.tupled, Artists.unapply)
    // scalastyle:on method.name

  }

}

@Singleton
class ArtistsDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
    extends ArtistsComponent
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val artists = lifted.TableQuery[ArtistsTable]

  def getAll: Future[Seq[Artists]] = db.run(artists.result)

}
