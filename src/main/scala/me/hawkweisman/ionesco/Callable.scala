package me.hawkweisman.ionesco

/** Trait for a JavaScript value that's callable (e.g. a function).
  *
  * Created by Eliza on 7/28/16.
  */
trait Callable {
  def apply(args: AnyRef*): AnyRef
}
