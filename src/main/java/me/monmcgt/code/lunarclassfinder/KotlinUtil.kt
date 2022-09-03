package me.monmcgt.code.lunarclassfinder

import java.lang.reflect.Method

fun Method.invokeStatic(vararg args: Any?): Any? {
    return invoke(null, *args)
}