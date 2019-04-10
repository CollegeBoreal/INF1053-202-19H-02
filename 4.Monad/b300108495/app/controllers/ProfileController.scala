package controllers

import dao.BandsDao
import javax.inject.{Inject, Singleton}
import models.Band
import play.api.libs.json.{Format, Json}
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

import scala.concurrent.ExecutionContext

@Singleton()
class ProfileController @Inject()(cc: ControllerComponents, profileDao: ProfileDao)(
  implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  implicit val fmt: Format[Profile] = Json.format[Profile]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { profile <- profileDao.getAll } yield Ok(Json.toJson(profile))
  }
}
