package me.hawkweisman.ionesco.nashorn

import jdk.nashorn.api.scripting.{JSObject => NashornJsObject}
import me.hawkweisman.ionesco.JsObject
import scala.collection.JavaConverters._

import scala.util.Try

/**
  * Created by Eliza on 7/27/16.
  */
class IonescoScriptObject(val obj: NashornJsObject)
extends JsObject {
  /**
    * Set a field in this object equal to the given value.
    *
    * If the field does not exist, it should be created.
    *
    * @param name  the name of the field to set
    * @param value the value to set the field to
    */
  override protected[this] def setField(name: String, value: AnyRef): Unit
    = obj.setMember(name, value)

  /**
    * Access a given field from this object
    *
    * @param name the name of the field to access
    */
  override protected def rawField(name: String): Try[AnyRef]
    = Try(obj getMember name)

  override protected def rawFieldOption(name: String): Option[AnyRef]
    = Option(obj getMember name)

  /**
    * @return the names of the fields in this object
    */
  override def names: Set[String] = obj.keySet.iterator.asScala.toSet

  /**
    * @return the raw untyped ([[Any]]) optional value of this object
    */
  override protected[this] def rawOption: Option[AnyRef]
    = Some(obj)

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  override protected[this] def rawTry: Try[AnyRef]
    = Try(obj)
}
