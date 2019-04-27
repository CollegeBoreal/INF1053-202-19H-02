package controllers

//import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime

import com.mohiva.play.silhouette.api.Silhouette
import io.swagger.annotations.{Api, ApiOperation}
import javax.inject.Inject
import models.auth.DefaultEnv
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

import scala.concurrent.Future
@Api(value = "Example data")
class ApplicationController @Inject()(components: ControllerComponents,
                                      silhouette: Silhouette[DefaultEnv])
    extends AbstractController(components) {

  val logger: Logger = Logger(this.getClass)

  @ApiOperation(value = "Get bad password value")
  def badPassword: Action[AnyContent] = silhouette.SecuredAction.async {
    implicit request =>
      if (logger.isDebugEnabled) {
        logger.debug(
          "Displaying headers :-------------------------------------------------------------------")
        val headers: Map[String, String] = request.headers.toSimpleMap
        for ((k, v) <- headers) {
          logger.debug(s"key: $k, value: $v")
        }
      }
      Future.successful(Ok(Json.obj("result" -> "qwerty1234")))
  }

}
