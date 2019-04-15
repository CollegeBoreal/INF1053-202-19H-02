package controllers

import dao.ProfileDao
import javax.inject.{Inject, Singleton}
import models.Profile
import play.api.libs.json.{Format, JsValue, Json}
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import Json.{obj, toJson}

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class ProfileController @Inject()(
    cc: ControllerComponents,
    profileDao: ProfileDao)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit val fmt: Format[Profile] = Json.format[Profile]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { profile <- profileDao.getAll } yield Ok(Json.toJson(profile))
  }
  def CreateProfile: Action[JsValue] = Action.async(parse.json) {
    implicit request =>
      request.body
        .validate[Profile]
        .map { profile =>
          profileDao.add(profile)
          Future.successful(Ok(toJson(profile)))
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
  }
}
