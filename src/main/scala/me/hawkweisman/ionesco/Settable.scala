package me.hawkweisman.ionesco

/** Trait for JavaScript objects with fields that can be set.
  *
  * In order to be `Settable`, an object must inherently also be [[Selectable]].
  * Essentially, the difference between `Settable` and [[Selectable]] is that
  * objects which are `Settable` implement `selectDynamic()` and
  * `updateDynamic()`,  while objects which are [[Selectable]] implement only
  * `selectDynamic()`.
  *
  * A side benefit of this trait is that once we can set the fields of an
  * object, we can also provide an implementation of Scala's `Map` API.
  *
  * @see [[Selectable]]
  *
  * Created by Eliza on 7/27/16.
  */
trait Settable
extends Selectable {

  /**
    * Set a field in this object equal to the given value.
    *
    * If the field does not exist, it should be created.
    *
    * @param name   the name of the field to set
    * @param value  the value to set the field to
    */
  protected[this] def setField(name: String, value: Any): Unit

  /**
    * Adds a field in this object with the given `name` and `value`.
    *
    * @param name   the name for the new field
    * @param value  the value for the new field
    */
  protected[this] def addField(name: String, value: Any): Unit

  @inline def updateDynamic(name: String)(value: Any): Unit
    = setField(name, value)


}
