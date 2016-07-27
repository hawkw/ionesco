package me.hawkweisman.ionesco

import scala.language.dynamics

/** Trait for JavaScript objects with fields that can be selected.
  *
  * Created by Eliza on 7/27/16.
  */
trait Selectable
extends Dynamic
  with Resolvable {

  /**
    * The type of a field access into this object.
    */
  type Select <: Resolvable

  /**
    * Access a given field from this object
    * @param name the name of the field to access
    */
  protected[this] def field(name: String): Select
  protected[this] def hasField(name: String): Boolean

  /**
    * @return the names of the fields in this object
    */
  def names: Set[String]

  @inline final def selectDynamic(name: String): Select
    = field(name)

}
