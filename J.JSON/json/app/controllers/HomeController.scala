package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import io.circe.generic.auto._
import io.circe.syntax._
import play.api.libs.circe.Circe

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Circe {

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

  case class Bar(bar: Int)
  case class Foo(foo: String, bar: Bar)

  val bar = Bar(1)
  val foo = Foo("foo", bar)

  /**
    * Create an Action to render a JSON object
    */
  def get() = Action {
    Ok(foo.asJson)
  }

  //parse json to case class
  def post = Action(circe.json[Foo]) { implicit request =>
    val isEqual = request.body == foo
    Ok(isEqual.toString)
  }
}
