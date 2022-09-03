package me.monmcgt.code.lunarbuiltinlauncher.httpservers.paths.lunar

import com.google.gson.Gson
import express.http.request.Request
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

abstract class Template {
    companion object {
        @JvmStatic
        val gson = Gson()
    }

    abstract fun add(app: express.Express): Unit

    protected fun checkInit(): Unit {
        if (!LauncherMain.INSTANCE.alreadyInited) {
            LauncherMain.INSTANCE.init()
        }
    }

    protected fun getJson(request: Request): String {
        try {
            BufferedReader(InputStreamReader(request.body)).use { bufferedReader ->
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                return stringBuilder.toString()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}