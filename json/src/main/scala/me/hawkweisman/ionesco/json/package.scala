package me.hawkweisman.ionesco

import org.json.JSONObject

import scala.language.implicitConversions

/** == Ionesco JSON ==
  *
  * Created by Eliza on 7/27/16.
  */
package object json {

  implicit def wrapJsonObject(obj: JSONObject): IonescoJsonObject
    = new IonescoJsonObject(obj)

}
