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
  override protected[this] def setField(name: String, value: Any): Unit
    = ???

  /**
    * Adds a field in this object with the given `name` and `value`.
    *
    * @param name  the name for the new field
    * @param value the value for the new field
    */
  override protected[this] def addField(name: String, value: Any): Unit = ???

  /**
    * Access a given field from this object
    *
    * @param name the name of the field to access
    */
  override def rawField(name: String): Try[Any]
  = Try(obj get name)

  override def rawFieldOption(name: String): Option[Any]
  = Option(obj opt name)

  /**
    * @return the names of the fields in this object
    */
  override def names: Set[String] = obj.keys.asScala.toSet

  /**
    * @return the raw untyped ([[Any]]) optional value of this object
    */
  override protected[this] def rawOption: Option[Any] = Some(obj)

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  override protected[this] def rawTry: Try[Any] = Success(obj)
}