package me.monmcgt.code.commands.impl.overlay.impl.autoqueue

import me.monmcgt.code.Debug
import me.monmcgt.code.bedwarsstatslunarinject.apis.ApiMainWrapper
import me.monmcgt.code.bedwarsstatslunarinject.stats.BedwarsStatsData
import me.monmcgt.code.classes.MessageHook
import me.monmcgt.code.classes.S2C_Message
import me.monmcgt.code.commands.*
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.settings.`BedwarsOverlay$Command$AutoQueue$Settings`
import me.monmcgt.code.commands.impl.overlay.impl.autoqueue.util.HypixelStatsCache
import me.monmcgt.code.config.ConfigManager
import me.monmcgt.code.helpers.*
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.tasks.hypixel.HypixelBedwarsTask
import me.monmcgt.code.util.checkModuleCommandArgs
import me.monmcgt.code.util.toChatMessage
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Consumer
import kotlin.properties.Delegates

@CommandInfo(
    ["autoqueue", "aq"],
)
object `BedwarsOverlay$Command$AutoQueue` : CommandAbstract() {
    override val subCommands: MutableList<CommandAbstract>
        get() = mutableListOf(
            `BedwarsOverlay$Command$AutoQueue$Toggle`,
            `BedwarsOverlay$Command$AutoQueue$Settings`(),
        )

    /*companion object {
        var enabled = false
            set(value) {
                field = value

            }
    }*/
    var enabled = false
        set(value) {
            field = value
            run()
        }

    var autoQueueThreads = mutableListOf<AutoQueueThread>()

    override fun execute() {
        if (checkModuleCommandArgs(this)) {
            runSubCommand = true
        }
    }

    fun run() {
        if (enabled) {
            clearAutoQueueThreads()
            Thread {
                Thread.sleep(250)
                start()
            }.start()
        } else {
            /*if (autoQueueThread != null) {
                try {
                    // copy and make sure it's not null
                    val autoQueueThreadNotNull = autoQueueThread!!
                    autoQueueThreadNotNull.interrupted.set(true)
                    autoQueueThreadNotNull.interrupt()
                    autoQueueThread = null
                } catch (_: Exception) {
                }
            }*/
            clearAutoQueueThreads()
        }
    }

    fun clearAutoQueueThreads() {
        autoQueueThreads.forEach {
            try {
                it.interrupted.set(true)
                it.interrupt()

                Thread {
                    val b = it.executors.awaitTermination(20, TimeUnit.SECONDS)
                    if (!b) {
                        it.executors.shutdownNow()
                    }
                }.start()
            } catch (_: Exception) {
            }
        }
    }

    fun start() {
        AutoQueueThread().start()
    }

    class AutoQueueThread : Thread() {
        val executors = Executors.newFixedThreadPool(30)

        var chatConsumer: Consumer<S2C_Message> by Delegates.notNull()
        var playerStatsConsumer: Consumer<BedwarsStatsData> by Delegates.notNull()

        val interrupted = AtomicBoolean(false)
        var timeLeft = 10000
        var firstTimeLeftInNewGame = -1
        var isSlashWhoCalled = false

        val exclusion = mutableSetOf<String>()
        var lastCommandTime = 0L
        var state: State = State.WAITING_SELF
            @Synchronized get
            @Synchronized set(value) {
                Debug.println("[AutoQueueThread] State changed to $value (Before: $field)")
                field = value
            }

        override fun run() {
            autoQueueThreads.add(this)
            HypixelBedwarsTask.party {
                exclusion.clear()
                exclusion.add(LauncherMain.INSTANCE.selfName)
                if (it.isInParty) {
                    it.players
                        .filter { it2 -> it2 !in exclusion }
                        .forEach(exclusion::add)
                }
                exclusion.forEach { it2 ->
                    Debug.println("Added $it2 to autoqueue exclusion")
                }
//                Executors.newFixedThreadPool(30).submit(::run2)
                run2()
            }
        }

        fun run2() {
            setConsumer()
            MessageHook.addS2C(chatConsumer)
            "/play ${`BedwarsOverlay$Command$AutoQueue$Settings`.mode.command}".toChatMessage().sendChat()
            lastCommandTime = System.currentTimeMillis()
        }

        fun changeGame(forceLobby: Boolean = false) {
            var forceLobby1 = forceLobby
            if (interrupted.get()) {
                return
            }
            if (state != State.CHANGING_GAME) {
                Debug.println("[AutoQueueThread] Returning because state is not changing game (State: $state)")
                return
            }

            Debug.println("[AutoQueueThread] Changing game (forceLobby: $forceLobby1)")
            "${yellow}Changing game...".toChatMessage().addPrefix().printChat()
//            firstTimeLeftInNewGame = -1
            val delay = ConfigManager.config.bedwarsOverlay.autoQueue.delay
            state = State.SLEEPING
            while (System.currentTimeMillis() - lastCommandTime < delay * 1000 || forceLobby1) {
                if (interrupted.get()) {
                    try {
                        Thread.interrupted()
                    } catch (_: Exception) {
                    }
                    return
                }
                if ((timeLeft <= delay || forceLobby1) && firstTimeLeftInNewGame != -1) {
                    "/lobby".toChatMessage().sendChat()
                    lastCommandTime = System.currentTimeMillis()
                    "${yellow}Leaving game because the game is about to start.".toChatMessage().addPrefix().printChat()
//                    "${yellow}You will be returned to the lobby in $delay seconds".toChatMessage().addPrefix().printChat()
                    // you will join the game in $delay seconds
                    "${yellow}You will be return to the game in $delay seconds.".toChatMessage().addPrefix().printChat()
//                    state = State.SLEEPING
                    Thread.sleep(delay * 1000L)
                    forceLobby1 = false
                    break
                }
                Thread.sleep(100)
            }
            state = State.WAITING_SELF
            lastCommandTime = System.currentTimeMillis()
            firstTimeLeftInNewGame = -1
            "/play ${`BedwarsOverlay$Command$AutoQueue$Settings`.mode.command}".toChatMessage().sendChat()
            lastCommandTime = System.currentTimeMillis()
            isSlashWhoCalled = false
        }

        override fun interrupt() {
            MessageHook.removeS2C(chatConsumer)
            super.interrupt()
        }

        fun setConsumer() {
            playerStatsConsumer = Consumer {
                executors.submit {
                    Debug.println("[AutoQueue] Received stats for ${it.`hypixelStats$Response`.player.displayname}")
                    Debug.println("[AutoQueue] FKDR: ${it.fkdr}, Star: ${it.bedwarsLevel}")
                    if (state != State.WAITING) {
//                        Debug.println("[AutoQueue] Returning from consumer because state is $state")
//                        return@Consumer
                        return@submit
                    }
                    if (interrupted.get()) {
//                        Debug.println("[AutoQueue] Returning from consumer because interrupted")
//                        return@Consumer
                        return@submit
                    }

                    val fkdr = it.fkdr
                    // 2 decimal digits fkdr
                    val fkdr2 = String.format("%.2f", fkdr)
                    val star = it.bedwarsLevel

                    val max_fkdr = `BedwarsOverlay$Command$AutoQueue$Settings`.maxFKDR
                    val max_level = `BedwarsOverlay$Command$AutoQueue$Settings`.maxLevel

                    val isFkdrMoreThanMax = fkdr >= max_fkdr
                    val isStarMoreThanMax = star >= max_level
                    val isNicked = it.fkdr == -1f
                    val isNickedAndLeaveIfNicked = isNicked && `BedwarsOverlay$Command$AutoQueue$Settings`.leaveIfNicked

                    if (!it.isFromCache) {
                        HypixelStatsCache.addStats(HypixelStatsCache.HypixelStats(it.`hypixelStats$Response`.player.displayname,
                            isNicked, BedwarsStatsData(it.bedwarsLevel, it.fkdr, it.winrate, it.winstreak, it.`hypixelStats$Response`)))
                    }

                    if (isFkdrMoreThanMax || isStarMoreThanMax || isNickedAndLeaveIfNicked) {
                        if (isNickedAndLeaveIfNicked) {
                            // nicked
                            "${dark_aqua}${it.`hypixelStats$Response`.player.displayname}${yellow} is nicked.".toChatMessage().addPrefix().printChat()
                        } else {
//                            "${yellow}Found player ${dark_aqua}${it.`hypixelStats$Response`.player.displayname}${yellow} with FKDR: ${if (isFkdrMoreThanMax) red else lime}$fkdr${yellow} and Star: ${if (isStarMoreThanMax) red else lime}$star${yellow}".toChatMessage().addPrefix().printChat()
                            "${yellow}Found player ${dark_aqua}${it.`hypixelStats$Response`.player.displayname}${yellow} with FKDR: ${if (isFkdrMoreThanMax) red else lime}$fkdr2${yellow} and Star: ${if (isStarMoreThanMax) red else lime}$star${yellow}.".toChatMessage().addPrefix().printChat()
                        }
                        state = State.CHANGING_GAME
                        changeGame()
                    } else {
//                        Debug.println("[AutoQueue] FKDR: $fkdr, Star: $star")
                    }
                }
            }

            chatConsumer = Consumer {
//                val leaveCommands = arrayListOf("/hub", "/lobby", "/leave", "/l")
                executors.submit {
                    if (state != State.WAITING && state != State.WAITING_SELF) {
//                        return@Consumer
                        Debug.println("[AutoQueue] Returning from chat consumer because state is $state")
                        return@submit
                    }
                    if (interrupted.get()) {
//                        return@Consumer
                        try {
                            Thread.interrupted()
                        } catch (_: Exception) {
                        }
                        return@submit
                    }

                    val message = it.message.trim()

                    // §r§e§r§eThe game starts in §r§a§r§c4§r§e seconds!§r§e§r
                    val timeLeftRegex1 = Regex("§r§e§r§eThe game starts in §r§a§r§e(\\d+)§r§e seconds!§r§e§r")
                    val timeLeftRegex2 = Regex("§r§e§r§eThe game starts in §r§a§r§b(\\d+)§r§e seconds!§r§e§r")
                    val timeLeftRegex3 = Regex("§r§e§r§eThe game starts in §r§a§r§6(\\d+)§r§e seconds!§r§e§r")
                    val timeLeftRegex4 = Regex("§r§e§r§eThe game starts in §r§a§r§c(\\d+)§r§e seconds!§r§e§r")
                    // §r§e§r§7dPwlswnsdud§r§r§r§e has joined (§r§b15§r§r§r§e/§r§b16§r§r§r§e)!§r§e§r
                    val playerJoinedRegex =
//                        Regex("§r§e§r§7(.+?)§r§r§r§e has joined \\(§r§b(\\d+)§r§r§r§e/§r§b(\\d+)§r§r§r§e!§r§e§r\\)")
                        Regex("§r§e§r§(.)(.+?)§r§r§r§e has joined \\(§r§b(\\d+)§r§r§r§e/§r§b(\\d+)§r§r§r§e\\)!§r§e§r")

                    when {
                        GameStartChecker.check(message) -> {
                            `BedwarsOverlay$Command$AutoQueue`.enabled = false
//                            "${yellow}Game started, disabling ${dark_aqua}autoqueue".toChatMessage().addPrefix().printChat()
                            "${yellow}Game started, disabling ${dark_aqua}autoqueue".toChatMessage().addPrefix().printChatAfter(delay = 500L)
                            GameStartChecker.clear()
                        }
                        timeLeftRegex1.matches(message) -> {
                            /*if (state == State.SLEEPING) {
                                return@submit
                            }*/
                            if (interrupted.get()) {
                                return@submit
                            }

                            timeLeft = timeLeftRegex1.find(message)!!.groupValues[1].toInt()
                            if (firstTimeLeftInNewGame == -1) {
                                firstTimeLeftInNewGame = timeLeft
                            }
                            Debug.println("[AutoQueue] Time left: $timeLeft")
                        }
                        timeLeftRegex2.matches(message) -> {
                            if (interrupted.get()) {
                                return@submit
                            }

                            timeLeft = timeLeftRegex3.find(message)!!.groupValues[1].toInt()
                            if (firstTimeLeftInNewGame == -1) {
                                firstTimeLeftInNewGame = timeLeft
                            }
                            Debug.println("[AutoQueue] Time left: $timeLeft")
                        }
                        timeLeftRegex3.matches(message) -> {
                            if (interrupted.get()) {
                                return@submit
                            }

                            timeLeft = timeLeftRegex3.find(message)!!.groupValues[1].toInt()
                            if (firstTimeLeftInNewGame == -1) {
                                firstTimeLeftInNewGame = timeLeft
                            }
                            Debug.println("[AutoQueue] Time left: $timeLeft")
                        }
                        timeLeftRegex4.matches(message) -> {
                            if (interrupted.get()) {
                                return@submit
                            }

                            timeLeft = timeLeftRegex4.find(message)!!.groupValues[1].toInt()
                            if (firstTimeLeftInNewGame == -1) {
                                firstTimeLeftInNewGame = timeLeft
                                /*if (firstTimeLeftInNewGame <= 5 && firstTimeLeftInNewGame != -1) {
                                    state = State.CHANGING_GAME
                                    changeGame(forceLobby = true)
//                                    firstTimeLeftInNewGame = -1
                                }*/
                            }
                            Debug.println("[AutoQueue] Time left: $timeLeft")
                        }
                        playerJoinedRegex.matches(message) -> {
                            val player = playerJoinedRegex.find(message)!!.groupValues[2]
                            Debug.println("[AutoQueue] Player joined: $player")
                            if (exclusion.contains(player)) {
                                if (interrupted.get()) {
//                                    return@Consumer
                                    return@submit
                                }

                                if (state == State.WAITING_SELF && !isSlashWhoCalled) {
                                    executors.submit {
//                                        Debug.println("[AutoQueue] Calling /who ...")
                                        Thread.sleep(1500)
                                        lastCommandTime = System.currentTimeMillis()
                                        isSlashWhoCalled = true
                                        HypixelBedwarsTask.who { it2 ->
                                            it2
                                                .filter { it3 -> !exclusion.contains(it3) }
                                                .forEach { it4 ->
                                                    executors.submit {
//                                                        ApiMainWrapper.checkBedwarsStats(it4, playerStatsConsumer)
                                                        val hypixelStatsByName = HypixelStatsCache.getHypixelStatsByName(it4)
                                                        if (hypixelStatsByName == null) {
                                                            ApiMainWrapper.checkBedwarsStats(it4, playerStatsConsumer)
                                                        } else {
                                                            hypixelStatsByName.bedwarsStatsData.isFromCache = true
                                                            playerStatsConsumer.accept(BedwarsStatsData(hypixelStatsByName.bedwarsStatsData.bedwarsLevel, hypixelStatsByName.bedwarsStatsData.fkdr, hypixelStatsByName.bedwarsStatsData.winrate, hypixelStatsByName.bedwarsStatsData.winstreak, hypixelStatsByName.bedwarsStatsData.`hypixelStats$Response`))
                                                        }
                                                    }
                                                }
                                        }
//                                        Debug.println("[AutoQueue] Called /who")
                                        state = State.WAITING
                                    }
                                }
                            } else {
                                if (interrupted.get()) {
//                                    return@Consumer
                                    return@submit
                                }
                                /*if (state == State.SLEEPING) {
                                    return@submit
                                }*/

                                executors.submit {
//                                    ApiMainWrapper.checkBedwarsStats(player, playerStatsConsumer)
                                    val hypixelStatsByName = HypixelStatsCache.getHypixelStatsByName(player)
                                    if (hypixelStatsByName == null) {
                                        ApiMainWrapper.checkBedwarsStats(player, playerStatsConsumer)
                                    } else {
                                        hypixelStatsByName.bedwarsStatsData.isFromCache = true
                                        playerStatsConsumer.accept(BedwarsStatsData(hypixelStatsByName.bedwarsStatsData.bedwarsLevel, hypixelStatsByName.bedwarsStatsData.fkdr, hypixelStatsByName.bedwarsStatsData.winrate, hypixelStatsByName.bedwarsStatsData.winstreak, hypixelStatsByName.bedwarsStatsData.`hypixelStats$Response`))
                                    }
                                }
                            }
                        }
                    }

                    if (firstTimeLeftInNewGame in 1..8) {
                        state = State.CHANGING_GAME
                        changeGame(forceLobby = true)
//                                    firstTimeLeftInNewGame = -1
                    }
                }
            }
        }

        object GameStartChecker {
            val bufferedHelper = BufferedHelper<String>(getGameStartString().size)

            fun check(message: String): Boolean {
                return message.trim() == "§r§r§r    §r§e§lIron, Gold, Emerald and Diamond from generators§r"

                /*bufferedHelper.swap(message.trim())

                if (bufferedHelper.allNotNull()) {
                    *//*for ((index, string) in bufferedHelper.buffer.withIndex()) {
                        val want = getGameStartString()[index]
                        if (string != want) {
                            return false
                        }
                    }
                    return true*//*
                    for (i in 0..getGameStartString().size) {
                        val has = bufferedHelper.get(i)
                        val want = getGameStartString()[i]
                        if (has != want) {
                            return false
                        }
                    }
                }

                return false*/
            }

            fun clear() {
                bufferedHelper.clear()
            }

            fun getGameStartString(): Array<String> {
                /*

                §r§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r
                §r§r§r                                  §r§f§lBed Wars§r
                §r
                §r§r§r     §r§e§lProtect your bed and destroy the enemy beds.§r
                §r§r§r      §r§e§lUpgrade yourself and your team by collecting§r
                §r§r§r    §r§e§lIron, Gold, Emerald and Diamond from generators§r
                §r§r§r                  §r§e§lto access powerful upgrades.§r
                §r
                §r§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r

                */

                return arrayOf(
                    "§r§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r",
                    "§r§r§r                                  §r§f§lBed Wars§r",
                    "§r",
                    "§r§r§r     §r§e§lProtect your bed and destroy the enemy beds.§r",
                    "§r§r§r      §r§e§lUpgrade yourself and your team by collecting§r",
                    "§r§r§r    §r§e§lIron, Gold, Emerald and Diamond from generators§r",
                    "§r§r§r                  §r§e§lto access powerful upgrades.§r",
                    "§r",
                    "§r§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬§r"
                )
            }
        }

        enum class State {
            // means thread sleeping
            SLEEPING,

            // means working normally and waiting for the game to start
            WAITING,

            // waiting for self join message
            WAITING_SELF,

            // changing game
            CHANGING_GAME,
        }
    }
}