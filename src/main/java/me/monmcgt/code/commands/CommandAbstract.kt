package me.monmcgt.code.commands

import me.monmcgt.code.helpers.bold
import me.monmcgt.code.helpers.red

abstract class CommandAbstract : CommandBaseUtility() {
    var runSubCommand = false
    var runPostExecute = false
    var runPreSubCommand = false
    open val subCommands: MutableList<CommandAbstract> = mutableListOf()

    var previousCommandName = arrayOf<String>()

    var commandInfo: CommandInfo

    lateinit var rawMessage: String
    var aliases: Array<String>

    init {
        commandInfo = javaClass.getAnnotation(CommandInfo::class.java) ?: throw IllegalArgumentException("Command class must be annotated with @CommandInfo")
        aliases = commandInfo.aliases
    }

    fun preExecute(rawMessage: String, args: List<String>) {
        runSubCommand = false
        runPostExecute = false
        runPreSubCommand = false

        this.rawMessage = rawMessage
        this.args = args.toTypedArray()
        execute()

        if (runSubCommand) {
            val nextArgsArray = args.toTypedArray().copyOfRange(1, args.size)
            val nextCommandName = args[0]

            val filter = subCommands.filter {
                /*it.aliases.forEach {  it2 ->
                    if (it2.equals(nextCommandName, true)) {
                        return@filter true
                    }
                }
                return@filter false*/
                it.isThisCommand(nextCommandName)
            }.toMutableList()

            /*if (filter.isNotEmpty() && runPreSubCommand) {
                preRunSubCommand(filter)
            }*/

            if (filter.isEmpty()) {
                val message = "$red${bold}Command not found!"
                ChatMessage(message).addPrefix().printChat()
            } else {
                if (runPreSubCommand) {
                    preRunSubCommand(filter)
                }

                filter.forEach {
                    it.previousCommandName += aliases[0]
                    it.preExecute(rawMessage, nextArgsArray.toList())
                }
            }

            if (runPostExecute) {
                postExecute()
            }
        }
    }

    open fun postExecute() {
    }

    open fun preRunSubCommand(subCommands: MutableList<CommandAbstract>) {
    }

    fun isThisCommand(command: String): Boolean {
        aliases.forEach {
            if (it.equals(command, true)) {
                return true
            }
        }
        return false
    }
}