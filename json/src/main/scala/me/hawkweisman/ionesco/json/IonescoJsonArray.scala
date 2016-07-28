package me.hawkweisman.ionesco.json

import me.hawkweisman.ionesco.JsArray
import org.json.JSONArray

import scala.language.postfixOps
import scala.util.Try

/**
  * Created by Eliza on 7/28/16.
  */
class IonescoJsonArray(val array: JSONArray)
extends JsArray {
  @inline
  override def length: Int = array length

  @inline
  override protected def rawIndex(idx: Int): Try[AnyRef]
    = Try(array get idx)

  @inline
  override protected def rawIndexOption(idx: Int): Option[AnyRef]
    = Option(array opt idx)
}
