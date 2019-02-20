import java.sql.Timestamp
import java.text.{SimpleDateFormat}

import play.api.libs.json._

package object controllers {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd:mm::ss")
    def reads(json: JsValue) =
      JsSuccess(new Timestamp(format.parse(json.as[String]).getTime))
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

}
