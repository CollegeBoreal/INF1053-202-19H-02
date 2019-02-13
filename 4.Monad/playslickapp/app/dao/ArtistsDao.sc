package dao

import javax.inject.{Inject, Singleton}
import models.{Artists, Customer}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted


import scala.concurrent.{ExecutionContext, Future}

trait ArtistsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class ArtistsTable(tag: Tag) extends Table[Artists](tag, "artists") {

    // scalastyle:off magic.number
    def artists: Rep[Int] = column[Int]("artists", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name", O.Length(45, varying = true))

    def band: Rep[String] =
      column[String]("phone", O.Length(45, varying = true))
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Artists] =
      (artists ?, name, band) <> (Artists.tupled, Artists.unapply)
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
