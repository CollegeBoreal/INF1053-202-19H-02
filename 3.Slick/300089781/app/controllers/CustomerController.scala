package controllers

import javax.inject.{Inject, Singleton}
import models.{Customer, Message}
import play.api.libs.json.{Format, Json}
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}

@Singleton()
class CustomerController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {
  implicit val fmt: Format[Customer] = Json.format[Customer]

  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(Customer(Some(1), "Amelie Dubois")))
  }
}
