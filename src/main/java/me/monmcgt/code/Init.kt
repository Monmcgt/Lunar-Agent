package me.monmcgt.code

import java.io.File

object Init {
    @JvmStatic
    fun init() {
        LUNAR_AGENT_FOLDER = with(LUNAR_1_8_FOLDER) {
            val agentFolder = File(this, ".${COMMAND_PREFIX[0]}")
            if (!agentFolder.exists()) {
                agentFolder.mkdirs()
            }
            return@with agentFolder
        }
    }
}