package me.hawkweisman.ionesco

import jdk.nashorn.api.scripting.ScriptObjectMirror

/**
  * Created by Eliza on 7/27/16.
  */
package object nashorn {

  implicit def wrapScriptObject(obj: ScriptObjectMirror): IonescoNashornObject
  = new IonescoNashornObject(obj)
}
