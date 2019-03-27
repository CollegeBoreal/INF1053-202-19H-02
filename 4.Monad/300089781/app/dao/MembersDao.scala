/*
 * DAO Implementation for Products Table.
 * If a repository pattern is desired instead, it can be built on top of many of the same components.
 */

package dao

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import models.{Member, Product}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait MembersComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._
  import slick.lifted.ProvenShape

  class MemberTable(tag: Tag) extends Table[Member](tag, "members") {
    def band: Rep[Int] =
      column[Int]("band", O.PrimaryKey, O.AutoInc)

    // scalastyle:off magic.number
    def artist: Rep[Int] =
      column[Int]("artist", O.Length(31, varying = true))
    def since: Rep[Timestamp] =
      column[Timestamp]("since", O.Length(127, varying = true))

    // scalastyle:off method.name
    override def * : ProvenShape[Member] =
      (band.?, artist, since) <> (Member.tupled, Member.unapply)
    // scalastyle: on method.name
  }
}

@Singleton()
class MembersDao @Inject()(
                            protected val dbConfigProvider: DatabaseConfigProvider)(
                            implicit executionContext: ExecutionContext)
  extends MembersComponent
    with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  val members = TableQuery[MemberTable]

  def getAll: Future[Seq[Member]] = db.run(members.result)
}