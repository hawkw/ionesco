package me.hawkweisman.ionesco

import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

/** A JavaScript value of unknown type which may or may not exist, which we can
  * attempt to resolve as a [[JsValue]].
  *
  * This should define the `as[JsValue]` operation. The trait is named
  * `Resolvable` because "As-Able" sounds bad.
  *
  * @see JsValue
  *
  * Created by Eliza on 7/26/16.
  */
trait Resolvable {
  /**
    * @return the raw untyped ([[Any]]) optional value of this object
    */
  protected[this] def rawOption: Option[Any]

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  protected[this] def rawTry: Try[Any]

  /**
    * Attempt to resolve this JS object as a `T`.
    *
    * @tparam T the type to resolve this object as. `T` must be a [[JsValue]].
    * @return   a `Success[T]` if this object exists and is of type `T`, a
    *           `Failure` if it does not exist or could not be resolved as
    *           type `T`.
    */
  @inline final def as[T: JsValue#Element : ClassTag]: Try[T]
    = rawTry flatMap {
      case it: T => Success(it)
        // TODO: this exception needs to have a new type
      case it => Failure(new Exception(
        s"Could not represent $it as a(n) ${implicitly[ClassTag[T]]}"))
    }

  @inline final def asOption[T: JsValue#Element : ClassTag]: Option[T]
    = rawOption flatMap {
      case it: T => Some(it)
      case _ => None
    }

}
