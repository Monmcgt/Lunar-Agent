package me.monmcgt.code.tasks.hypixel

import me.monmcgt.code.bedwarsstatslunarinject.consumers.ChatConsumer
import me.monmcgt.code.classes.MessageHook
import me.monmcgt.code.classes.S2C_Message
import me.monmcgt.code.commands.ChatMessage
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.commands.sendChat
import me.monmcgt.code.helpers.FormatHelperJava
import me.monmcgt.code.helpers.red
import me.monmcgt.code.util.TimeCountUtil
import me.monmcgt.code.util.removeColourCodes
import me.monmcgt.code.util.toChatMessage
import java.util.concurrent.Executors
import java.util.function.Consumer
import kotlin.properties.Delegates

object HypixelBedwarsTask {
    private val executors = Executors.newFixedThreadPool(30)

    /*private val consumer: Consumer<S2C_Message> = Consumer {

    }*/

    fun api(callBack: (String) -> Unit) {
        val rawApiMessage = "§aYour new API key is §r§b"
        executors.submit {
            val timeCountUtil = TimeCountUtil.newInstance(5)
            var consumer: Consumer<S2C_Message> by Delegates.notNull()
            var finished = false
            consumer = Consumer<S2C_Message> {
                if (finished) {
                    MessageHook.removeS2C(consumer)
                } else if (timeCountUtil.isTimeOver()) {
                    sendDidNotReceiveResponse()
                    MessageHook.removeS2C(consumer)
                } else {
                    if (it.message.trim().startsWith(rawApiMessage)) {
                        val apiKey =
                            it.message.trim().split(rawApiMessage.toRegex(), 2).toTypedArray()[1].removeColourCodes()
                                .trim()

                        finished = true

                        it.cancel = true

//                        callBack(apiKey)
                        executors.submit {
                            callBack(apiKey)
                        }
                    }
                }
            }
            MessageHook.addS2C(consumer)
            "/api new".toChatMessage().sendChat()
        }
    }

    fun who(callBack: (List<String>) -> Unit) {
        val rawWhoMessage = "§r§b§lONLINE: "
        executors.submit {
            val timeCountUtil = TimeCountUtil.newInstance(5)
            var consumer: Consumer<S2C_Message> by Delegates.notNull()
            var finished = false
            val players: MutableList<String> = ArrayList()
            consumer = Consumer<S2C_Message> {
                if (finished) {
                    MessageHook.removeS2C(consumer)
                } else if (timeCountUtil.isTimeOver()) {
                    sendDidNotReceiveResponse()
                    MessageHook.removeS2C(consumer)
                } else {
                    if (it.message.trim().startsWith(rawWhoMessage)) {
                        val raw =
                            it.getPlainMessage().split(rawWhoMessage.removeColourCodes().toRegex(), 2)
                                .toTypedArray()[1].trim()
                        val split = raw.split(",")

                        split
                            .map { it2 -> it2.replace("\\[.*\\]".toRegex(), "").trim() }
                            .forEach(players::add)

                        finished = true

                        it.cancel = true

                        /*players.forEach { it2 ->
                            println("Player: $it2")
                        }*/

//                        println("raw message: " + it.message)
//                        callBack(players)
                        executors.submit {
                            callBack(players)
                        }
                    }
                }
            }
            MessageHook.addS2C(consumer)
            ChatMessage("/who").sendChat()
        }
    }

    /*fun party(callBack: (List<String>) -> Unit) {
        val rawLine = "§b§m-----------------------------------------------------§r";
        executors.submit {
            val timeCountUtil = TimeCountUtil.newInstance(5)
            var consumer: Consumer<S2C_Message> by Delegates.notNull()
            var finished = false
            val players: MutableList<String> = ArrayList()
            consumer = Consumer<S2C_Message> {
                if (finished) {
                    MessageHook.removeS2C(consumer)
                } else if (timeCountUtil.isTimeOver()) {
                    sendDidNotReceiveResponse()
                    MessageHook.removeS2C(consumer)
                } else {
                    if (it.message.trim().startsWith(rawLine)) {

                    }
                }
            }
        }
    }*/

    fun party(callback: (PartyPlayers) -> Unit) {
        val customPartyRunnable = CustomPartyRunnable(callback)
        executors.submit(customPartyRunnable)
    }

    private fun sendDidNotReceiveResponse() {
        /*var message = "${red}Did not receive any response from the server."
//        ChatMessage(message).addPrefix().printChat()
        message = FormatHelperJava.addPrefix(message)
        message.toChatMessage().printChat()*/
        FormatHelperJava.addPrefix("${red}Did not receive any response from the server.").toChatMessage().printChat()
    }

    data class PartyPlayers(
        var isInParty: Boolean = false,
        var players: MutableList<String> = ArrayList(),
    )

    private class CustomPartyRunnable(val callBack: (PartyPlayers) -> Unit) : Runnable {
        val lines = ArrayList<S2C_Message>()
        var consumer: Consumer<S2C_Message> by Delegates.notNull()

        var inProgress = false

        val blueLines = "§9§m-----------------------------------------------------§r"

        override fun run() {
            setConsumer()
            MessageHook.addS2C(consumer)
            "/p list".toChatMessage().sendChat()
        }

        fun runAfter() {
            val partyPlayers = PartyPlayers()

            for (line in lines) {
                if (line.message.trim() == "§cYou are not currently in a party.§r") {
                    callBack(partyPlayers)
                    return
                }

                val plainMessage = line.getPlainMessage()
                if (!plainMessage.contains(": ")) {
                    continue
                }

                val players =
                    ChatConsumer.replaceRanksAndOnlineOffline(plainMessage.split(": ".toRegex(), 2)[1].trim()).trim()
                        .split("\\s+".toRegex()).map { it.trim() }.map { ChatConsumer.replaceRanksAndOnlineOffline(it) }.filter { it.isNotEmpty() }
                players.forEach {
                    partyPlayers.players.add(it)
                }
            }

            if (partyPlayers.players.size != 0) {
                partyPlayers.isInParty = true
            }

            callBack(partyPlayers)
        }

        fun setConsumer() {
            consumer = Consumer<S2C_Message> {
                /*if (finished) {
                runAfter()
            } else {
                lines.add(it)
            }*/
                val timeCountUtil = TimeCountUtil.newInstance(5)
                if (timeCountUtil.isTimeOver()) {
                    sendDidNotReceiveResponse()
                    MessageHook.removeS2C(consumer)
                } else if (!inProgress && lines.size == 0 && it.message == blueLines) {
                    inProgress = true
                    lines.add(it)
                    it.cancel = true
                } else if (inProgress) {
                    lines.add(it)
                    it.cancel = true
                    if (inProgress && it.message == blueLines) {
                        Thread {
                            runAfter()
                        }.start()
                        MessageHook.removeS2C(consumer)
                    }
                }
            }
        }
    }
}