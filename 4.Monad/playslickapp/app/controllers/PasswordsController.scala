package controllers

import dao.PasswordsDao
import javax.inject.{Inject, Singleton}
import models.Passwords
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class PasswordsController @Inject()(
    cc: ControllerComponents,
    passwordsDao: PasswordsDao)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  implicit val fmt: Format[Passwords] = Json.format[Passwords]

  def getAll: Action[AnyContent] = Action.async { implicit request =>
    for { passwords <- passwordsDao.getAll } yield Ok(Json.toJson(passwords))
  }
}
