package me.monmcgt.code.commands

import me.monmcgt.code.COMMAND_PREFIX
import me.monmcgt.code.Debug
import me.monmcgt.code.PREFIX
import me.monmcgt.code.classes.C2S_Message
import me.monmcgt.code.classes.MessageHook
import me.monmcgt.code.commands.impl.config.api.`ApiKey$Command`
import me.monmcgt.code.commands.impl.debug.`Debug$Command`
import me.monmcgt.code.commands.impl.modules.Modules
import me.monmcgt.code.commands.impl.overlay.`BedwarsOverlay$Command`
import me.monmcgt.code.commands.impl.server.`Server$Command`
import me.monmcgt.code.commands.impl.test.Test1
import me.monmcgt.code.commands.impl.test.Test2
import me.monmcgt.code.commands.impl.test.Test3
import me.monmcgt.code.commands.impl.test.Test4
import me.monmcgt.code.helpers.*
import me.monmcgt.code.util.ClassScanner
import java.util.concurrent.Executors
import java.util.function.Consumer

private val commandsExecutor = Executors.newFixedThreadPool(150)

private val commands = mutableListOf<CommandAbstract>()

object CommandManager {
    private val chatConsumer: Consumer<C2S_Message> = Consumer {
        if (onMessage(it.message)) {
            it.cancel = true
        }
    }

    private fun onMessage(raw: String): Boolean {
        if (!raw.startsWith(PREFIX)) {
            return false
        }

        val message = raw.substring(PREFIX.length)
        val split = message.split("\\s+".toRegex())

        if (split.isEmpty()) {
            return false
        }

        var equal = false
        COMMAND_PREFIX.forEach {
            if (split[0].equals(it, true)) {
                equal = true
            }
        }
        if (!equal) {
            return false
        }

        if (split.size < 2 || split[1].equals("help", true)) {
            sendHelpMessage()
            return true
        }

        val args = split.drop(2)

        val commands = commands.filter { it.isThisCommand(split[1]) }

        if (commands.isEmpty()) {
            val message = "${red}${bold}Command not found!"
            ChatMessage(message).addPrefix().printChat()
        } else {
            commandsExecutor.submit {
                commands.forEach {
                    it.preExecute(raw, args)
                }
            }
        }

        return true
    }

    private fun sendHelpMessage() {
        ChatMessage("${yellow}${bold}${underline}Available commands${yellow}${bold}:").addPrefix().printChat()
//        ChatMessage("---------------------------------").addPrefix().printChat()
        commands.forEach {
            val prefix = COMMAND_PREFIX[0]
            val alias = it.aliases[0]
            if (!alias.startsWith("test") && alias != "debug") {
                ChatMessage(" $grey$bold$rightArrow$reset $dark_aqua/$prefix $alias").addPrefix().printChat()
            }
        }
    }

    fun init() {
        addCommands()
        MessageHook.addC2S(chatConsumer)
        /*scanClasses().forEach {
            println("Found command: ${it.simpleName}")
            registerCommand(it)
        }
        println("commands.size = ${commands.size}")
        ClassScanner.getAllClasses().forEach {
            println(" >>>> ${it.name}")
        }
        println("commands.size2 = ${commands.size}")*/
    }

    private fun addCommands() {
        arrayOf(

            Modules(),
            `BedwarsOverlay$Command`(),
            `ApiKey$Command`(),
            `Server$Command`,

            Test1(),
            Test2(),
            Test3(),
            Test4,

            `Debug$Command`(),

        ).forEach(::registerCommand)
    }

    private fun scanClasses(): List<Class<*>> {
        val list = mutableListOf<Class<*>>()
        ClassScanner.getAllClasses("me.monmcgt.code.commands.impl").forEach {
            var valid = false
            val annotations = it.annotations
            if (annotations.isNotEmpty()) {
                annotations.forEach { it2 ->
                    if (checkValidClass(it, it2)) {
                        var superClass = it
                        while (superClass != CommandAbstract::class.java) {
                            if (superClass.superclass == null || superClass.superclass == java.lang.Object::class.java) {
                                break
                            }
                            superClass = superClass.superclass
                        }
                        if (superClass == CommandAbstract::class.java) {
                            valid = true
                        }
                    }
                }
            }
            if (valid) {
                list.add(it)
            }
        }
        return list
    }

    private fun checkValidClass(clazz: Class<*>, annotation: Annotation): Boolean {
        Debug.println("Checking class: ${clazz.simpleName}")
        if (annotation.annotationClass == RegisterCommand::class) {
            if (clazz.annotations.any { it.annotationClass == CommandInfo::class }) {
                Debug.println("TRUE")
                return true
            }
        }

        Debug.println("FALSE")
        return false
    }

    private fun registerCommand(command: CommandAbstract) {
        commands.add(command)
    }

    /*private fun registerCommand(clazz: Class<*>) {
        registerCommand(clazz.newInstance() as CommandAbstract)
    }*/
}