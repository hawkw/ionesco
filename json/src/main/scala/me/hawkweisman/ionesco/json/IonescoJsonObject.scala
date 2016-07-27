package me.hawkweisman.ionesco
package json

import org.json.JSONObject

import scala.collection.JavaConverters._
import scala.reflect.ClassTag
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
  override protected[this] def setField(name: String, value: Any): Unit
    // TODO: handle cases where the Value is not a JSON-able thing
    = obj.put(name, value)

  /**
    * Access a given field from this object
    *
    * @param name the name of the field to access
    */
  override def rawField(name: String): Try[AnyRef]
  = Try(obj get name)

  override def rawFieldOption(name: String): Option[AnyRef]
  = Option(obj opt name)

  /**
    * @return the names of the fields in this object
    */
  override def names: Set[String] = obj.keys.asScala.toSet

  /**
    * @return the raw untyped ([[Any]]) optional value of this object
    */
  override protected[this] def rawOption: Option[JSONObject] = Some(obj)

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  override protected[this] def rawTry: Try[JSONObject] = Success(obj)
}