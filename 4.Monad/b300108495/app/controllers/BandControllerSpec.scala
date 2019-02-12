package controllers

import common.SlickInMemorySpec
import play.api.mvc.{Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class BandControllerSpec extends SlickInMemorySpec with Results {
  "The band controller" should {
    "respond to getAll with a list of bands" in {
      val controller: BandController =
        app.injector.instanceOf[BandController]
      val result: Future[Result] = controller.getAll.apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      status(result) mustEqual OK
      contentType(result) mustEqual Some("application/json")
      bodyText must include("tmblr-stl-med")
    }
  }
}
