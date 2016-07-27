package me.hawkweisman.ionesco

import scala.language.postfixOps
import scala.reflect.{classTag, ClassTag}
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
      case it if classTag[T] == classTag[JsObject] =>
        Resolvable.jsonObjectToIonesco(it)
      case it: T => Success(it)
        // TODO: this exception needs to have a new type
      case it => Failure(new Exception(
        s"Could not represent $it as a(n) ${classTag[T]}"))
    }

  @inline final def asOption[T: JsValue#Element : ClassTag]: Option[T]
    = rawOption flatMap {
      case it if classTag[T] == classTag[JsObject] =>
        Resolvable.jsonObjectToIonesco(it) toOption
      case it: T => Some(it)
      case _ => None
    }

}

object Resolvable {
  /**
    * An attempt to load the `JSONObject` class from `org.json`.
    */
  private[this] lazy val jsonObjectClass: Try[Class[_]]
  = Try(Class.forName("org.json.JSONObject"))
  /**
    * An attempt to load the `IonescoJSONObject` class from `ionesco.json`.
    */
  private[this] lazy val ionescoJsonObjectClass: Try[Class[_]]
  = Try(Class.forName("me.hawkweisman.ionesco.json.IonescoJsonObject"))

  /**
    * Convert an `org.json` `JSONObject` into an `IonescoJsonObject`.
    *
    * This is Extremely Unsafe because it violates type safety a bit, as we
    * have to take in a parameter of type `Any` (since we don't always have
    * `JSONObject` on the classpath, and we might not always know what that is).
    * The returned object is of type LOL WHATEVER so you don't have to cast it
    * at the call site, because if we're already violating type safety, may
    * as well go the whole way, I guess...
    *
    * @note that this will return an exception of `org.json` and `me
    * .hawkweisman.ionesco.json` aren't on the classpath, but you knew that.
    * @param obj the object to convert to an `IonescoJsonObject`. If this isn't
    *            of type `JSONObject`, you're gonna have an Extremely Bad Time.
    * @tparam B LOL WHATEVER
    * @return the wrapped `IonescoJSONObject`, or an exception if you've been
    *         naughty.
    */
  private def jsonObjectToIonesco[B](obj: Any): Try[B]
    = for {
        // try to access the JSONObject class, or fail if it wasn't loaded
        jsonObj <- jsonObjectClass
        // try to access the class of IonescoJsonObject or fail
        ionescoObj <- ionescoJsonObjectClass
        // try to get the constructor for an IonescoJsonObject from a JSONObject
        ionescoObjCtor <- Try(ionescoObj.getConstructor(jsonObj))
        // try to use the constructor to construct an IonescoJsonObject
        newObj <- Try(ionescoObjCtor.newInstance(obj.asInstanceOf[Object]))
      } yield newObj.asInstanceOf[B]
}
