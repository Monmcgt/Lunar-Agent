package me.monmcgt.code.classes

data class S2C_Message(
    val message: String,
    var cancel: Boolean = false
) {
    fun getPlainMessage(): String {
        // § follow by 0-9 or e or a or b or d or f or k or l or m or n or o or r
        return message.replace("§[0-9eabdflmnor]".toRegex(), "")
    }
}
