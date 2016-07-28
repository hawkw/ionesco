package me.hawkweisman.ionesco

/**
  * Created by Eliza on 7/26/16.
  */
trait JsArray
extends Indexable
  with IndexedSeq[Resolvable] {


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

}