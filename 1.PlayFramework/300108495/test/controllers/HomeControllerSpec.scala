package controllers


import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Hello Boreal!")
    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Hello Boreal!")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Hello Boreal!")
    }
    "afficher le API hello du routage" in {
      val request = FakeRequest(GET, "/hello/name=safaa")
      val hello = route(app, request).get

      status(hello) mustBe OK
      contentType(hello) mustBe Some("text/plain")
      contentAsString(hello) must include ("name=safaa")
    }
    "afficher le API json du routage" in {
      val request = FakeRequest(GET, "/json")
      val json = route(app, request).get

      status(json) mustBe  UNSUPPORTED_MEDIA_TYPE
      contentType(json) mustBe Some("text/html")
      contentAsString(json) must include("")
    }
  }
}
