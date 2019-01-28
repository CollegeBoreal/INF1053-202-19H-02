package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Writes
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def mapage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.mapage())
  }

  case class Location(lat: Double, long: Double)

  case class Place(name: String, location: Location)

  object Place {

    var list: List[Place] = {
      List(
        Place(
          "Sandleford",
          Location(51.377797, -1.318965)
        ),
        Place(
          "Watership Down",
          Location(51.235685, -1.309197)
        )
      )
    }

    def save(place: Place) = {
      list = list ::: List(place)
    }
  }
  implicit val locationWrites: Writes[Location] = (
    (JsPath \ "lat").write[Double] and
      (JsPath \ "long").write[Double]
    )(unlift(Location.unapply))

  implicit val placeWrites: Writes[Place] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "location").write[Location]
    )(unlift(Place.unapply))

  def listPlaces = Action {
    val json = Json.toJson(Place.list)
    Ok(json)
  }
}
