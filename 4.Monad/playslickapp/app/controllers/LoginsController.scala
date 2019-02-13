package controllers

import dao.LoginsDao
import javax.inject.{Inject, Singleton}
import models.Logins
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class LoginsController @Inject()(cc: ControllerComponents, loginsDao: LoginsDao)(
    implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit val fmt: Format[Logins] = Json.format[Logins]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { logins <- loginsDao.getAll } yield Ok(Json.toJson(logins))
  }
}
