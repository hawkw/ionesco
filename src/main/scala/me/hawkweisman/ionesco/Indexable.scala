package me.hawkweisman.ionesco
import scala.util.Try

/** Trait representing a JS array
  *
  * Created by Eliza on 7/28/16.
  */
trait Indexable {

  def length: Int

  /** Attempt to index the JS array.
    *
    * @inheritdoc
    *
    * The returned [[Index]] can then be resolved to the desired type
    *
    * @param idx the index to access
    * @return    an [[Index]] object representing the indexing attempt
    * @throws    IndexOutOfBoundsException if `i < 0` or `length <= i`
    */
  @inline def apply(idx: Int): Index
    = idx match {
      case i if idx < 0 =>
        throw new IndexOutOfBoundsException(s"index $i < 0")
      case i if length <= idx =>
        throw new IndexOutOfBoundsException(s"index $i >= length ($length)")
      case i => new Index(i, this)
    }

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
    @inline override protected[ionesco] def rawField(name: String): Try[AnyRef]
    = for { obj: JsObject <- this.as[JsObject]
            raw <- obj rawField name }
      yield raw

    @inline
    override protected[ionesco] def rawFieldOption(name: String): Option[AnyRef]
    = for { obj: JsObject <- this.asOption[JsObject]
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
