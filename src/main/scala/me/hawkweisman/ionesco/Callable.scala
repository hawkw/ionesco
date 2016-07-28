package me.hawkweisman.ionesco

import scala.util.Try

/** Trait for a JavaScript value that's callable (e.g. a function).
  *
  * Created by Eliza on 7/28/16.
  */
trait Callable {
  def apply(args: AnyRef*): Try[AnyRef]
}
