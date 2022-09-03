package me.monmcgt.code.classes

import java.util.function.Consumer

object MessageHook {
    private var c2sConsumer: MutableList<Consumer<C2S_Message>> = mutableListOf()
        @Synchronized get
        @Synchronized set
    private var s2cConsumer: MutableList<Consumer<S2C_Message>> = mutableListOf()
        @Synchronized get
        @Synchronized set

    fun addC2S(consumer: Consumer<C2S_Message>) {
        c2sConsumer.add(consumer)
    }

    fun addS2C(consumer: Consumer<S2C_Message>) {
        s2cConsumer.add(consumer)
    }

    fun removeC2S(consumer: Consumer<C2S_Message>) {
        c2sConsumer.remove(consumer)
    }

    fun removeS2C(consumer: Consumer<S2C_Message>) {
        s2cConsumer.remove(consumer)
    }

    fun c2s(message: C2S_Message): C2S_Message {
        c2sConsumer.toMutableList().forEach { it.accept(message) }
        return message
    }

    fun s2c(message: S2C_Message): S2C_Message {
        s2cConsumer.toMutableList().forEach { it.accept(message) }
        return message
    }
}