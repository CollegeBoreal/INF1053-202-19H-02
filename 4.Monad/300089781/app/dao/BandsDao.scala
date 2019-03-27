/*
 * DAO Implementation for Products Table.
 * If a repository pattern is desired instead, it can be built on top of many of the same components.
 */

package dao

import javax.inject.{Inject, Singleton}
import models.Band
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait BandsComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class BandTable(tag: Tag) extends Table[Band](tag, "bands") {
    def band: Rep[Int] = column[Int]("band", O.PrimaryKey, O.AutoInc)

    // scalastyle:off magic.number
    def name: Rep[String] =
      column[String]("name", O.Length(127, varying = true))
    def description: Rep[String] =
      column[String]("description", O.Length(511, varying = true))
    // scalastyle:on magic.number

    // scalastyle:off method.name
    override def * : ProvenShape[Band] =
      (band.?, name, description) <> (Band.tupled, Band.unapply)
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
}