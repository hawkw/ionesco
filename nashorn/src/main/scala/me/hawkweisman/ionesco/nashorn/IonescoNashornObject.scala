package me.hawkweisman.ionesco.nashorn

import jdk.nashorn.api.scripting.ScriptObjectMirror
import me.hawkweisman.ionesco.JsObject
import scala.collection.JavaConverters._

import scala.util.Try

/**
  * Created by Eliza on 7/27/16.
  */
class IonescoNashornObject(val obj: ScriptObjectMirror)
extends JsObject {
  /**
    * Set a field in this object equal to the given value.
    *
    * If the field does not exist, it should be created.
    *
    * @param name  the name of the field to set
    * @param value the value to set the field to
    */
  override protected[this] def setField(name: String, value: Any): Unit = ???

  /**
    * Access a given field from this object
    *
    * @param name the name of the field to access
    */
  override protected def rawField(name: String): Try[Any] = ???
  override protected def rawFieldOption(name: String): Option[Any] = ???

  /**
    * @return the names of the fields in this object
    */
  override def names: Set[String] = obj.keySet.iterator.asScala.toSet

  /**
    * @return the raw untyped ([[Any]]) optional value of this object
    */
  override protected[this] def rawOption: Option[Any] = ???

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  override protected[this] def rawTry: Try[Any] = ???
}