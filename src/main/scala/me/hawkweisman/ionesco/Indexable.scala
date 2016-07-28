package me.hawkweisman.ionesco
import scala.util.Try

/** Trait representing a JS array
  *
  * Created by Eliza on 7/28/16.
  */
trait Indexable {

  @inline def apply(i: Int): Index
    = new Index(i, this)

  protected def rawIndex(idx: Int): Try[AnyRef]
  protected def rawIndexOption(idx: Int): Option[AnyRef]

  class Index(val idx: Int, protected val parent: Indexable)
//  extends Resolvable {
  extends Selectable {
    /**
      * Access a given field from this object
      *
      * @param name the name of the field to access
      */
    @inline
    override protected def rawField(name: String): Try[AnyRef]
      = for { obj <- this.as[JsObject]
              raw <- obj rawField name }
        yield raw

    @inline
    override protected def rawFieldOption(name: String): Option[AnyRef]
      = for { obj <- this.asOption[JsObject]
              raw <- obj rawFieldOption name }
        yield raw

    /**
      * @return the names of the fields in this object
      */
    override def names: Set[String]
      = this.as[JsObject] map { _.names } getOrElse Set()

    /**
      * @return the raw untyped ([[Any]]) optional value of this object
      */
    @inline override protected[this] def rawOption: Option[AnyRef]
      = parent rawIndexOption idx

    /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
      */
    @inline override protected[this] def rawTry: Try[AnyRef]
      = parent rawIndex idx
  }

}
