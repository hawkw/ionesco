package me.hawkweisman.ionesco

import jdk.nashorn.api.scripting.ScriptObjectMirror

import scala.language.implicitConversions

/**
  * Created by Eliza on 7/27/16.
  */
package object nashorn {

  implicit def wrapScriptObject(obj: ScriptObjectMirror): IonescoScriptObject
    = new IonescoScriptObject(obj)
}
