package me.hawkweisman.ionesco

/**
  * Created by Eliza on 7/26/16.
  */
trait JsObject[Self <: Selectable]
extends Settable
  with Map[String, Self#Select] {

  @inline override def size: Int = names.size

  override def get(name: String): Option[Select]
    = if (this hasField name) Some(field(name))
      else None


}
