package me.hawkweisman.ionesco

import scala.language.dynamics
import scala.util.Try

/** Trait for JavaScript objects with fields that can be selected.
  *
  * Created by Eliza on 7/27/16.
  */
trait Selectable
extends Dynamic
  with Resolvable {

//  /**
//    * The type of a field access into this object.
//    */
//  type Select <: SelectLike

  class Select(val name: String, protected val parent: Selectable)
  extends Selectable {

    /**
      * @return the names of the fields in this object
      */
    override def names: Set[String]
      = this.as[JsObject] map { _.names } getOrElse Set()

    /**
      * @return the raw untyped ([[Any]]) optional value of this object
      */
    override protected[this] def rawOption: Option[Any] = ???

    /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
      */
    override protected[this] def rawTry: Try[Any]
    = parent.as[JsObject] flatMap { _ rawField name }

    /**
      * Access a given field from this object
      *
      * @param name the name of the field to access
      */
    override protected[this] def rawField(name: String): Try[Any]
      = this.as[JsObject] flatMap { _ rawField name }
  }

  /**
    * Access a given field from this object
    * @param name the name of the field to access
    */
  protected[this] def rawField(name: String): Try[Any]

  @inline def contains(name: String): Boolean = names contains name

  /**
    * @return the names of the fields in this object
    */
  def names: Set[String]

  @inline final def selectDynamic(name: String): Select
    = new Select(name, this)

}
