package me.monmcgt.code.commands

abstract class CommandBaseUtility {
    lateinit var args: Array<String>

    abstract fun execute()

    fun printChat(message: String) {
        ChatMessage(message).printChat()
    }

    fun printChat(chatMessage: ChatMessage) {
        chatMessage.printChat()
    }

    fun sendChat(message: String) {
        ChatMessage(message).sendChat()
    }

    fun sendChat(chatMessage: ChatMessage) {
        chatMessage.sendChat()
    }

    fun printChatPrefix(message: String) {
        ChatMessage(message).addPrefix().printChat()
    }

    fun printChatPrefix(chatMessage: ChatMessage) {
        chatMessage.addPrefix().printChat()
    }
}