package controllers
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import dao.MembersDao
import javax.inject.{Inject, Singleton}
import models.Member
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class MemberController @Inject()(
    cc: ControllerComponents,
    membersDao: MembersDao)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

    implicit val fmt: Format[Member] = Json.format[Member]

    def getAll: Action[AnyContent] = Action.async { implicit request =>
      for { members <- membersDao.getAll } yield Ok(Json.toJson(members))
    }
  }

