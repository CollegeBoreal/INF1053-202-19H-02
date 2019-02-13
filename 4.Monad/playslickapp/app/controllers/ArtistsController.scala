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

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd:mm::ss")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
  implicit val fmt: Format[Artists] = Json.format[Artists]
  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { artists <- artistsDao.getAll } yield Ok(Json.toJson(artists))
  }
}
