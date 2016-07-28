package me.hawkweisman.ionesco


/**
  * Created by Eliza on 7/26/16.
  */
trait JsObject
extends Settable
  with Traversable[(String, Selectable#Select)] {

  // TODO: finish implementing Map for JsObject
//
//  @inline override def size: Int = names.size
//
//  override def get[JsAny](name: String): Option[JsAny]
//    = if (this contains name) this.selectDynamic(name)
//      else None
//
//  override def +=(kv: (String, JsAny)) = ???
//  override def -=(key: String) = ???
//
  override def foreach[U](f: ((String, Selectable#Select)) => U): Unit
    = for { name <- names }
      f((name, new Select(name, this)))
}
