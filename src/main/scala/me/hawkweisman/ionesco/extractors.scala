package me.hawkweisman.ionesco

import scala.language.postfixOps
import scala.reflect.ClassTag

/**
  * Created by Eliza on 7/23/16.
  */
object extractors {

  sealed abstract class JsExtractor[T: JsAny : ClassTag] {
    @inline def unapply(query: Resolvable): Option[T]
    = query.asOption[T]
  }

  object JsInt extends JsExtractor[Int]
  object JsDouble extends JsExtractor[Double]
  object JsBool extends JsExtractor[Boolean]
  object JsString extends JsExtractor[String]

//  object JsonArray extends Json[JSONArray] {
//    @inline def unapplySeq(query: Queryable): Option[IndexedSeq[Index]]
//    = query.asOption[JSONArray] map { IndexableJsonArray }
//  }

  object JsObject extends JsExtractor[JsObject] {
    @inline def unapplySeq(query: Resolvable): Option[Seq[(String, Resolvable)]]
//      = query.asOption[JsObject] map { _ toSeq }
      = ???
  }

}