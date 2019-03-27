/*
 * DAO Implementation for Products Table.
 * If a repository pattern is desired instead, it can be built on top of many of the same components.
 */

package dao

import javax.inject.{Inject, Singleton}
import models.Artist
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait ArtistsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ArtistTable(tag: Tag) extends Table[Artist](tag, "artists") {
    def artist: Rep[Int] = column[Int]("artist", O.PrimaryKey, O.AutoInc)

    // scalastyle:off magic.number
    def name: Rep[String] =
      column[String]("name", O.Length(127, varying = true))
    def description: Rep[String] =
      column[String]("description", O.Length(511, varying = true))
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Artist] =
      (artist.?, name, description) <> (Artist.tupled, Artist.unapply)
    // scalastyle: on method.name
  }
}

@Singleton()
class ArtistsDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit executionContext: ExecutionContext)
  extends ArtistsComponent
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val artists = TableQuery[ArtistTable]

  def getAll: Future[Seq[Artist]] = db.run(artists.result)
}