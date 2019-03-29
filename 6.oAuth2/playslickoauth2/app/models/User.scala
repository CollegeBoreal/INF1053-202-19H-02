package models

import com.mohiva.play.silhouette.api.Identity

case class User(user: Option[Int],
                key: String,
                active: Boolean,
                created: java.sql.Timestamp)
    extends Identity
