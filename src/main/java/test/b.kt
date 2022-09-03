package test

fun main() {
    val regex = Regex("§r§e§r§(.)(.+?)§r§r§r§e has joined \\(§r§b(\\d+)§r§r§r§e/§r§b(\\d+)§r§r§r§e\\)!§r§e§r")
//    val playerJoinedRegex = Regex("§r§e§r§7(.+?)§r§r§r§e has joined (§r§b(\\d+)§r§r§r§e/§r§b(\\d+)§r§r§r§e)!§r§e§r")
    val example = "§r§e§r§aMC_MzCGT§r§r§r§e has joined (§r§b8§r§r§r§e/§r§b8§r§r§r§e)!§r§e§r"
    println(regex.matches(example))
    println(regex.find(example)!!.groupValues[2]/*.substring(1, regex.find(example)!!.groupValues[1].length)*/)

    // §r§e§r§eThe game starts in §r§a§r§c4§r§e seconds!§r§e§r
    /*val regex = Regex("§r§e§r§eThe game starts in §r§a§r§e(\\d+)§r§e seconds!§r§e§r")
    val example = "§r§e§r§eThe game starts in §r§a§r§e20§r§e seconds!§r§e§r"
    println(regex.matches(example))
    println(regex.find(example)!!.groupValues[1])*/

    /*// regex that matches the following: ABC<anycharacter(onlyone)>DEF
    val regex1 = Regex("ABC(.)DEF")
    val example1 = "ABCDEDEF"
    println(regex1.matches(example1))
    println(regex1.find(example1)!!.groupValues[1])*/
}