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
  protected[this] def rawOption: Option[AnyRef]

  /** @return the raw untyped ([[Any]]) value of this object, as a [[Try]]
    */
  protected[this] def rawTry: Try[AnyRef]

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
        Resolvable jsObjFor it
      case it: T => Success(it)
        // TODO: this exception needs to have a new type
      case it => Failure(new Exception(
        s"Could not represent $it as a(n) ${classTag[T]}"))
    }

  @inline final def asOption[T: JsValue#Element : ClassTag]: Option[T]
    = rawOption flatMap {
      case it if classTag[T] == classTag[JsObject] =>
        Resolvable jsObjFor it toOption
      case it: T => Some(it)
      case _ => None
    }

}

object Resolvable {
  /**
    * An attempt to load the `JSONObject` class from `org.json`.
    */
  private[this] lazy val jsonObjectClass: Option[Class[_]]
    = Try(Class.forName("org.json.JSONObject")) toOption

  private[this] lazy val nashornObjectClass: Option[Class[_]]
    = Try(Class.forName("jdk.nashorn.api.scripting.ScriptObjectMirror"))
          .toOption

  /**
    * An attempt to load the `IonescoJSONObject` class from `ionesco.json`.
    */
  private[this] lazy val ionescoJsonObjectClass: Try[Class[_]]
    = Try(Class.forName("me.hawkweisman.ionesco.json.IonescoJsonObject"))

  /**
    * An attempt to load the `IonescoNashornObject` class from `ionesco
    * .nashorn`.
    */
  private[this] lazy val ionescoNashornObjectClass: Try[Class[_]]
    = Try(Class.forName("me.hawkweisman.ionesco.nashorn.IonescoScriptObject"))

  /**
    * Returns the Ionesco class for an object of the given `Class`
    * @param obj the object to find a class for
    * @return
    */
  @inline private[this] def ionescoClassFor(obj: AnyRef): Try[Class[_]]
    = obj.getClass match {
        case c if jsonObjectClass contains c => ionescoJsonObjectClass
        case c if nashornObjectClass contains c => ionescoNashornObjectClass
        case c =>
          Failure(new Exception(
            s"Couldn't find an Ionesco class for ${c.getCanonicalName}."))
      }

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
  private def jsObjFor[B](obj: AnyRef): Try[B]
    = for {
        // try to get the corresponding Ionesco class or fail
        ionescoClass <- ionescoClassFor(obj)
        // try to get the constructor for the Ioensco class
        ionescoObjCtor <- Try(ionescoClass.getConstructor(obj.getClass))
        // try to use the constructor to construct an IonescoJsObject
        newObj <- Try(ionescoObjCtor.newInstance(obj.asInstanceOf[Object]))
      } yield newObj.asInstanceOf[B]

}
