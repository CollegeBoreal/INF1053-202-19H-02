package controllers

import javax.inject._
import play.api._
import play.api.mvc._

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

  def hello(name: String) = Action {
    Ok("ce que je mets ici + " + name)
  }

  def action() = Action { implicit request =>
    anotherMethod("some para value")
    Ok("Got Request ["+ request + "]")
  }

  def anotherMethod(str: String)(implicit request: Request[_]) = {
    //
  }

  def json() = Action(parse.json) { implicit request =>
    Ok(" Got request[" + request + "]")
  }
}
