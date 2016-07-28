package me.hawkweisman.ionesco
package json

import org.json.JSONObject

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

/**
  * Created by Eliza on 7/27/16.
  */
class IonescoJsonObject(val obj: JSONObject)
extends JsObject {

  /**
    * Set a field in this object equal to the given value.
    *
    * If the field does not exist, it should be created.
    *
    * @param name  the name of the field to set
    * @param value the value to set the field to
    */
  @inline
  override protected[this] def setField(name: String, value: AnyRef): Unit
    // TODO: handle cases where the Value is not a JSON-able thing
    = obj.put(name, value)

  /**
    * Access a given field from this object
    *
    * @param name the name of the field to access
    */
  @inline override def rawField(name: String): Try[AnyRef]
  = Try(obj get name)

  @inline override def rawFieldOption(name: String): Option[AnyRef]
  = Option(obj opt name)

  /**
    * @return the names of the fields in this object
    */
  @inline override def names: Set[String] = obj.keys.asScala.toSet

  /**
    * @return the raw untyped ([[Any]]) optional value of this object
    */
  override protected[this] lazy val rawOption: Option[JSONObject]
    = Option(obj)

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  override protected[this] lazy val rawTry: Try[JSONObject] = Success(obj)
}