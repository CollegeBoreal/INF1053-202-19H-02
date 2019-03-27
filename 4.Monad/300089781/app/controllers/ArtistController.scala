package controllers

import dao.ArtistsDao
import javax.inject.{Inject, Singleton}
import models.{Artist, Artists}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class ArtistController @Inject()(
                                  cc: ControllerComponents,
                                  artistsDao: ArtistsDao)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  implicit val fmt: Format[Artist] = Json.format[Artist]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { artists <- artistsDao.getAll } yield Ok(Json.toJson(artists))
  }
}