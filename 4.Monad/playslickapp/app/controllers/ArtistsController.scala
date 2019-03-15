package controllers

import java.sql.Timestamp
import java.text.SimpleDateFormat

import dao.ArtistsDao
import javax.inject.{Inject, Singleton}
import models.Artists
import play.api.libs.json._
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

import scala.concurrent.ExecutionContext

@Singleton()
class ArtistsController @Inject()(
    cc: ControllerComponents,
    artistsDao: ArtistsDao)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {


  implicit val fmt: Format[Artists] = Json.format[Artists]
  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { artists <- artistsDao.getAll } yield Ok(Json.toJson(artists))
  }
}
