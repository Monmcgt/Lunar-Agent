package me.monmcgt.code

import java.io.File

const val PREFIX = "/"

val COMMAND_PREFIX = arrayOf("lunaragent", "la")

val LUNAR_1_8_FOLDER = File("${System.getProperty("user.home")}${File.separator}.lunarclient${File.separator}offline${File.separator}1.8")

lateinit var LUNAR_AGENT_FOLDER: File