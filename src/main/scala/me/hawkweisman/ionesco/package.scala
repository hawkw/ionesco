package me.hawkweisman

import scala.reflect.ClassTag
import scala.util.{Failure, Try}

/**
  * Created by Eliza on 7/26/16.
  */
package object ionesco
extends UnboxedUnion {

  /**
    * A Java `Enum`
    * @tparam E
    */
  type JsEnum[E <: Enum[E]] = Enum[E]

  /**
    * Types of JavaScript values
    *  + [[JsObject]]s
    *  + [[JsArray]]s
    *  + numbers: `Double`, `Int`
    *  + `Boolean` values
    *  + `String`s
    *  + [[java.lang.Enum]]s
    *  + Big numbers
    */
  // Todo: extract case classes as well?
  type JsValue
    = ∅ ∨ JsObject ∨ JsArray ∨ JsEnum[_] ∨ Boolean ∨ Double ∨ Int ∨
        String ∨ BigDecimal ∨ BigInt

}
