package me.monmcgt.code.util

import me.monmcgt.code.COMMAND_PREFIX
import me.monmcgt.code.Fields
import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.commands.ChatMessage
import me.monmcgt.code.commands.CommandAbstract
import me.monmcgt.code.commands.addPrefix
import me.monmcgt.code.commands.printChat
import me.monmcgt.code.helpers.*
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import me.monmcgt.code.lunarclassfinder.invokeStatic
import me.monmcgt.code.modules.ESP
import org.objectweb.asm.tree.MethodNode
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

val MCP_NAME__PATCH_NAME = mutableMapOf<String, String>()

//val usageMessage = "${yellow}${bold}${underline}Usage${yellow}${bold}:${reset} "
val usageMessage = "${yellow}${bold}${underline}Usage${reset}"

fun methodDescParam(desc: String): String {
    val firstParenthesis = desc.indexOf('(')
    val var0 = desc.substring(firstParenthesis + 1, desc.length - 1)

    val lastParenthesis = var0.lastIndexOf(')')
    val var1 = var0.substring(0, lastParenthesis)

    return var1
}

fun methodDescReturnType(desc: String): String {
    val firstParenthesis = desc.indexOf('(')
    val var0 = desc.substring(firstParenthesis + 1, desc.length - 1)

    val lastParenthesis = var0.lastIndexOf(')')
    val var1 = var0.substring(lastParenthesis + 1, var0.length)

    return var1
}

fun isThisMethodByReturnType(methodDesc: String, mcpName: String, primitive: Boolean = false): Boolean {
    val methodDescNew = methodDesc.replace('.', '/')
    val mcpNameNew = mcpName.replace('.', '/')

    val cleanDesc = methodDescReturnType(methodDescNew)

    if (!MCP_NAME__PATCH_NAME.containsKey(mcpNameNew)) {
        val patchName = LunarClassFinderMain.LunarClassFinderMAIN.getPatchName(mcpNameNew)
        MCP_NAME__PATCH_NAME[mcpNameNew] = patchName
    }

    val patchName = MCP_NAME__PATCH_NAME[mcpNameNew]
    val patchNameTemp = if (primitive) {
        patchName
    } else {
        "L$patchName"
    }

    return cleanDesc == patchNameTemp
}

fun isThisMethodByReturnType(methodNode: MethodNode, mcpName: String, primitive: Boolean = false): Boolean {
    val methodDesc = methodNode.desc
    return isThisMethodByReturnType(methodDesc, mcpName, primitive)
}

fun commandUsageMessage(commands: Array<String>): String {
    return " $grey$bold$rightArrow$reset $dark_aqua/${COMMAND_PREFIX[0]} ${commands.joinToString(" ")}"
}

/**
 * @return false if the args is empty
 */
fun checkModuleCommandArgs(commandAbstract: CommandAbstract): Boolean {
    val args = commandAbstract.args
    val aliases = commandAbstract.aliases
    val subCommands = commandAbstract.subCommands

    return if (args.isEmpty()) {
        var message = "$usageMessage $grey$bold$rightArrow$reset $dark_aqua/${COMMAND_PREFIX[0]} ${aliases[0]} <args>"
        ChatMessage(message).addPrefix().printChat()
        printAvailableArgsMessage()
        subCommands.forEach {
            /*message = " $grey$bold$rightArrow$reset $yellow${it.aliases[0]}"
            ChatMessage(message).addPrefix().printChat()*/
            message = " $grey$bold$rightArrow$reset $dark_aqua${it.aliases[0]}"
            ChatMessage(message).addPrefix().printChat()
        }
        false
    } else {
        true
    }
}

fun checkModuleCommandArgsAndSetRunSubCommand(commandAbstract: CommandAbstract): Boolean {
    if (checkModuleCommandArgs(commandAbstract)) {
        commandAbstract.runSubCommand = true
        return true
    } else {
        return false
    }
}

fun printAvailableCommandMessage() {
    ChatMessage("${yellow}${bold}${underline}Available commands${yellow}${bold}:").addPrefix().printChat()
}

fun printAvailableArgsMessage() {
    ChatMessage("${yellow}${bold}${underline}Available arguments${yellow}${bold}:").addPrefix().printChat()
}

fun printBlankLineInChat(hasPrefix: Boolean = false) {
    var chatMessage = ChatMessage("")
    if (hasPrefix) {
        chatMessage = chatMessage.addPrefix()
    }
    chatMessage.printChat()
}

fun getMinecraftObj(): Any {
    return ESP.minecraftClass.getMethod(Fields.MINECRAFT_GET_MINECRAFT_METHOD_NAME).invokeStatic() ?: throw IllegalStateException("Failed to get minecraft obj")
}

fun printModuleEnableOrDisableMessage(moduleName: String, isEnabled: Boolean) {
    val message = "${dark_aqua}${moduleName} ${yellow}is now ${if (isEnabled) "${lime}enabled" else "${red}disabled"}$yellow."
    ChatMessage(message).addPrefix().printChat()
}

fun printModuleSettingEnableOrDisableMessage(moduleName: String, settingName: String, isEnabled: Boolean) {
    val message = "${dark_aqua}${moduleName} ${pink}${settingName} ${yellow}is now ${if (isEnabled) "${lime}enabled" else "${red}disabled"}$yellow."
    ChatMessage(message).addPrefix().printChat()
}

fun printModuleSettingsCurrentlyEnableOrDisableMessage(moduleName: String, settingName: String, isEnabled: Boolean) {
    // similar to above one but change now to currently
    val message = "${dark_aqua}${moduleName} ${pink}${settingName} ${yellow}is currently ${if (isEnabled) "${lime}enabled" else "${red}disabled"}$yellow."
    ChatMessage(message).addPrefix().printChat()
}

fun printModuleSettingsValueMessage(moduleName: String, settingName: String, value: String) {
    "$dark_aqua$moduleName $pink$settingName ${yellow}is now set to $orange$value$yellow.".toChatMessage().addPrefix().printChat()
}

fun printModuleSettingsCurrentValueMessage(moduleName: String, settingName: String, value: String) {
    "$dark_aqua$moduleName $pink$settingName ${yellow}is currently set to $orange$value$yellow.".toChatMessage().addPrefix().printChat()
}

fun String.toChatMessage(): ChatMessage {
    return ChatMessage(this)
}

fun isHypixel(): Boolean {
    return isHypixelIp(LauncherMain.INSTANCE.currentServerIp ?: "")
}

fun isHypixelIp(ip: String): Boolean {
    return ip.endsWith(".hypixel.net", true) || ip.endsWith(".hypixel.io", true) || ip.equals("hypixel.net", true) || ip.equals("hypixel.io", true)
}

fun String.checkBedwarsStats() {
    ApiMainWrapper.apiMainInstance.checkPlayer(this)
}

fun String.removeColourCodes(): String {
    // § follow by 0-9 or e or a or b or d or f or k or l or m or n or o or r
    return this.replace("§[0-9eabdflmnor]".toRegex(), "")
}

fun checkPlayerName(name: String): Boolean {
    // greater than 0 but less than or equal to 16
    val validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_"
    return name.length in 1..16 && name.all { validChars.contains(it) }
}

fun ExecutorService.tryToShutdown(timeout: Long = 1000L) {
    try {
        this.shutdown()
        val b = this.awaitTermination(timeout, TimeUnit.MILLISECONDS)
        if (!b) {
            this.shutdownNow()
        }
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}