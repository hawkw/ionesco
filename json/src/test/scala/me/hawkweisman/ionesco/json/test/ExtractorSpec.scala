package me.hawkweisman.ionesco
package json
package test

import json._
import extractors._
import org.json.JSONObject
import org.scalatest.{Matchers, OptionValues, TryValues, WordSpec}

/**
  * Created by Eliza on 7/23/16.
  */
class ExtractorSpec
extends WordSpec
  with Matchers
  with TryValues
  with OptionValues {

  val simpleJs: JsObject = new JSONObject(
    """{ "int": 1,
      |  "string": "a string",
      |  "bool": true,
      |  "double": 0.5
           }""".stripMargin)

  "The JsInt extractor" when {
    "used with a simple JSON field access for an int" should {
      "match the int in a pattern match" in {
        simpleJs.int should matchPattern { case JsInt(_) => }
      }
      "extract the int to the correct value" in {
        val JsInt(it) = simpleJs.int
        it shouldEqual 1
      }
    }
    "used with field accesses for other types" should {
      "not match a bool" in {
        simpleJs.bool should not matchPattern { case JsInt(_) => }
      }
      "not match a double" in {
        simpleJs.double should not matchPattern { case JsInt(_) => }
      }
      "not match a string" in {
        simpleJs.string should not matchPattern { case JsInt(_) => }
      }
    }
  }

  "The JsDouble extractor" when {
    "used with a query for a double" should {
      "match the double  in a pattern match" in {
        simpleJs.double should matchPattern { case JsDouble(_) => }
      }
      "extract the int to the correct value" in {
        val JsDouble(it) = simpleJs.double
        it shouldEqual 0.5
      }
    }
    "used with queries for other types" should {
      "not match a bool" in {
        simpleJs.bool should not matchPattern { case JsDouble(_) => }
      }
      "not match an int" in {
        simpleJs.int should not matchPattern { case JsDouble(_) => }
      }
      "not match a string" in {
        simpleJs.string should not matchPattern { case JsDouble(_) => }
      }
    }
  }

  "The JsBool extractor" when {
    "used with a query for a bool" should {
      "match the bool in a pattern match" in {
        simpleJs.bool should matchPattern { case JsBool(_) => }
      }
      "extract the int to the correct value" in {
        val JsBool(it) = simpleJs.bool
        it shouldEqual true
      }
    }
    "used with queries for other types" should {
      "not match a double" in {
        simpleJs.double should not matchPattern { case JsBool(_) => }
      }
      "not match an int" in {
        simpleJs.int should not matchPattern { case JsBool(_) => }
      }
      "not match a string" in {
        simpleJs.string should not matchPattern { case JsBool(_) => }
      }
    }
  }


  "The JsString extractor" when {
    "used with a query for a bool" should {
      "match the bool in a pattern match" in {
        simpleJs.string should matchPattern { case JsString(_) => }
      }
      "extract the int to the correct value" in {
        val JsString(it) = simpleJs.string
        it shouldEqual "a string"
      }
    }
    "used with queries for other types" should {
      "not match a double" in {
        simpleJs.double should not matchPattern { case JsString(_) => }
      }
      "not match an int" in {
        simpleJs.int should not matchPattern { case JsString(_) => }
      }
      "not match a bool" in {
        simpleJs.bool should not matchPattern { case JsString(_) => }
      }
    }
  }

}