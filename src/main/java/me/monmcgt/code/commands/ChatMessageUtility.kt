package me.monmcgt.code.commands

import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import java.util.concurrent.Executors

private val executors = Executors.newFixedThreadPool(20)

fun ChatMessage.addPrefix(): ChatMessage {
    message = me.monmcgt.code.helpers.addPrefix(message)
    return this
}

fun ChatMessage.printChat(end: String = "\n") {
    LauncherMain.INSTANCE.printChatMessage(message)
}

fun ChatMessage.sendChat(end: String = "\n") {
    LauncherMain.INSTANCE.sendChatMessage(message)
}

fun ChatMessage.printChatAfter(end: String = "\n", delay: Long = 0) {
    executors.submit {
        Thread.sleep(delay)
        this.printChat(end)
    }
}

fun ChatMessage.sendChatAfter(end: String = "\n", delay: Long = 0) {
    executors.submit {
        Thread.sleep(delay)
        this.sendChat(end)
    }
}