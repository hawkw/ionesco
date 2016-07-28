package me.hawkweisman.ionesco

import org.json.{JSONArray, JSONObject}

import scala.language.implicitConversions

/** == Ionesco JSON ==
  *
  * Created by Eliza on 7/27/16.
  */
package object json {

  implicit def wrapJsonObject(obj: JSONObject): IonescoJsonObject
    = new IonescoJsonObject(obj)

  implicit def wrapJsonArray(array: JSONArray): IonescoJsonArray
    = new IonescoJsonArray(array)

}
