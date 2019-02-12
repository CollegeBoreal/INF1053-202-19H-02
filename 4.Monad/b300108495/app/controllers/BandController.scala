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
class BandController @Inject()(cc: ControllerComponents, bandsDao: BandsDao)(
    implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit val fmt: Format[Band] = Json.format[Band]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { bands <- bandsDao.getAll } yield Ok(Json.toJson(bands))
  }
}
