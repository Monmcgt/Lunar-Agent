package me.monmcgt.code.modules

import me.monmcgt.code.Debug
import me.monmcgt.code.Fields
import me.monmcgt.code.bedwarsstatslunarinject.threads.PlayerWatchedThread
import me.monmcgt.code.commands.impl.modules.impl.esp.`ESP$Command`
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.`ESP$Command$Settings`
import me.monmcgt.code.commands.impl.modules.impl.esp.settings.colour.`ESP$Command$Settings$Colour`
import me.monmcgt.code.commands.impl.modules.util.ColourMode
import me.monmcgt.code.finders.MessageEventClassFinder
import me.monmcgt.code.listeners.JoinLeaveServerEventListener
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain
import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo
import me.monmcgt.code.lunarclassfinder.LunarClassFinderMain
import me.monmcgt.code.lunarclassfinder.invokeStatic
import me.monmcgt.code.util.isBot
import org.lwjgl.opengl.GL11
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

private val minecraftClassPatchName =
    LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/Minecraft")
private val renderManagerClassPatchName =
    LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/entity/RenderManager")

object ESP {
    @JvmStatic
    var isInit = false
        set(value) {
//            println("ESP isInit BEFORE: $field")
//            println("ESP isInit: $value")
            field = value
        }

    @JvmStatic
    lateinit var minecraftClass: Class<*>

    @JvmStatic
    lateinit var mc: Any

    @JvmStatic
    lateinit var renderManagerClass: Class<*>

    @JvmStatic
    lateinit var renderManager: Any

    @JvmStatic
    lateinit var declaredFieldX: Field

    @JvmStatic
    lateinit var declaredFieldY: Field

    @JvmStatic
    lateinit var declaredFieldZ: Field

    @JvmStatic
    lateinit var gl11: Class<*>

    @JvmStatic
    lateinit var glBlendFunc: Method

    @JvmStatic
    lateinit var glLineWidth: Method

    @JvmStatic
    lateinit var glEnabled: Method

    @JvmStatic
    lateinit var glDisable: Method

    @JvmStatic
    lateinit var glDepthMask: Method

    @JvmStatic
    lateinit var glColor4d: Method

    @JvmStatic
    lateinit var glColor4f: Method

    @JvmStatic
    var GL_SRC_ALPHA: Int = 0

    @JvmStatic
    var GL_ONE_MINUS_SRC_ALPHA: Int = 0

    @JvmStatic
    var GL_BLEND: Int = 0

    @JvmStatic
    var GL_TEXTURE_2D: Int = 0

    @JvmStatic
    var GL_DEPTH_TEST: Int = 0

    @JvmStatic
    var GL_LIGHTING: Int = 0

    @JvmStatic
    lateinit var pushMetrics: Method

    @JvmStatic
    lateinit var popMetrics: Method

    @JvmStatic
    lateinit var axisAlignedBBInstanceConstructor: Constructor<*>

    @Deprecated("use drawOutlineBoundingBox instead")
    @JvmStatic
    lateinit var func_181561_a: Method

    @JvmStatic
    lateinit var func_181563_a: Method

    @JvmStatic
    lateinit var glBegin: Method

    @JvmStatic
    lateinit var glEnd: Method

    @JvmStatic
    lateinit var glVertex3d: Method

    @JvmStatic
    fun onRender(): Unit {
//        println("ESP onRender")
        if (LauncherMain.INSTANCE.classLoader == null) {
            LauncherMain.INSTANCE.classLoader = MessageEventClassFinder.classLoader
            LauncherMain.INSTANCE.init2()
        }

        if (LauncherMain.INSTANCE.classLoader == null) {
            return
        }
        if (!isInit) {// Minecraft
            minecraftClass =
                LauncherMain.INSTANCE.classLoader.loadClass(minecraftClassPatchName.replace('/', '.'))
            mc = minecraftClass.getDeclaredMethod(Fields.MINECRAFT_GET_MINECRAFT_METHOD_NAME).invokeStatic()!!
            // get RenderManager
            renderManagerClass =
                LauncherMain.INSTANCE.classLoader.loadClass(renderManagerClassPatchName.replace('/', '.'))
            renderManager =
                minecraftClass.getDeclaredMethod(Fields.MINECRAFT_GET_RENDER_MANAGER_METHOD_NAME).invoke(
                    mc
                )
            // fields' name
            val fX = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[0].name
            val fY = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[1].name
            val fZ = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[2].name
            // fields' value
            declaredFieldX = renderManagerClass.getDeclaredField(fX)
            declaredFieldY = renderManagerClass.getDeclaredField(fY)
            declaredFieldZ = renderManagerClass.getDeclaredField(fZ)

//                val gl11 = Class.forName("org.lwjgl.opengl.GL11")
            /*gl11 = LauncherMain.INSTANCE.classLoader.loadClass("org.lwjgl.opengl.GL11")

            // GL11 Methods
            glBlendFunc =
                gl11.getMethod("glBlendFunc", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            glLineWidth = gl11.getMethod("glLineWidth", Float::class.javaPrimitiveType)
            glEnabled = gl11.getMethod("glEnable", Int::class.javaPrimitiveType)
            glDisable = gl11.getMethod("glDisable", Int::class.javaPrimitiveType)
            glDepthMask = gl11.getMethod("glDepthMask", Boolean::class.javaPrimitiveType)
            glColor4d = gl11.getMethod(
                "glColor4d",
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )
            glColor4f = gl11.getMethod(
                "glColor4f",
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType
            )*/

            // GL11 Fields
            /*GL_SRC_ALPHA = gl11.getField("GL_SRC_ALPHA").getInt(null) // 770
            GL_ONE_MINUS_SRC_ALPHA = gl11.getField("GL_ONE_MINUS_SRC_ALPHA").getInt(null) // 771
            GL_BLEND = gl11.getField("GL_BLEND").get(null) as Int // 3042
            GL_TEXTURE_2D = gl11.getField("GL_TEXTURE_2D").get(null) as Int // 3553
            GL_DEPTH_TEST = gl11.getField("GL_DEPTH_TEST").get(null) as Int // 2929
            GL_LIGHTING = gl11.getField("GL_LIGHTING").get(null) as Int // 2896*/

            /*println("GL_BLEND = $GL_BLEND")
            println("GL_TEXTURE_2D = $GL_TEXTURE_2D")
            println("GL_DEPTH_TEST = $GL_DEPTH_TEST")*/

            // AxisAlignedBB

//            println("Init success")

            // PushMetrics & PopMetrics
            /*pushMetrics =
                gl11.getMethod("glPushMatrix"*//*, Void::class.javaPrimitiveType*//*)
            popMetrics =
                gl11.getMethod("glPopMatrix"*//*, Void::class.javaPrimitiveType*//*)*/


            val func_181561_a_ClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/RenderGlobal")
            Debug.println("func_181561_a_ClassName = $func_181561_a_ClassName")
            val func_181561_a_Class =
                LauncherMain.INSTANCE.classLoader.loadClass(func_181561_a_ClassName.replace('/', '.'))
            func_181561_a_Class.methods.forEach {
                Debug.println("func_181561_a_Class.methods.forEach = ${it.name} | params = ${Arrays.toString(it.parameterTypes)} | returnType = ${it.returnType}")
            }
//            func_181561_a = func_181561_a_Class.getMethod(Fields.func_181561_a_name, Fields.AXIS_ALIGNED_BB_CLASS)
            func_181561_a_Class.methods.find { it.name == Fields.func_181561_a_name }?.let {
                func_181561_a = it
                Debug.println("Set func_181561_a = ${it.name}")

                axisAlignedBBInstanceConstructor = it.parameterTypes[0].constructors.first()
            }

            val func_181563_a_ClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/RenderGlobal")
            Debug.println("func_181563_a_ClassName = $func_181563_a_ClassName")
            val func_181563_a_Class =
                LauncherMain.INSTANCE.classLoader.loadClass(func_181563_a_ClassName.replace('/', '.'))
            func_181563_a_Class.methods.forEach {
                Debug.println("func_181563_a_Class.methods.forEach = ${it.name} | params = ${Arrays.toString(it.parameterTypes)} | returnType = ${it.returnType}")
            }
            func_181563_a_Class.methods.find { it.name == Fields.func_181563_a_name }?.let {
                func_181563_a = it
                Debug.println("Set func_181563_a = ${it.name}")
            }

            /*axisAlignedBBInstanceConstructor = Fields.AXIS_ALIGNED_BB_CLASS.getDeclaredConstructor(
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )*/




            isInit = true
        }

        if (!`ESP$Command`.enabled) {
            return
        }

//        return
//        println("EntityRenderer")
        try {
            var mySelf: PlayerInfo? = null
            for ((count, player) in LauncherMain.INSTANCE.playerEntitiesListInfo.withIndex()) {
                if (count == 0) {
                    mySelf = player
                    continue
                }

                var isBot = false

                if (JoinLeaveServerEventListener.isHypixel) {
                    if (`ESP$Command$Settings`.antiBotHypixel) {
                        /*if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
                            isBot = true
                        }*/
                        if (player.isBot()) {
                            isBot = true
                        }
                    }
                } else {
                    if (`ESP$Command$Settings`.antiBot) {
                        /*if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
//                        continue
                            isBot = true
                        }*/
                        if (player.isBot()) {
                            isBot = true
                        }
                    }
                }

                if (isBot) {
                    continue
                }

//                println("Player: ${player.name}")

                val renderPosX = declaredFieldX.get(renderManager) as Double
                val renderPosY = declaredFieldY.get(renderManager) as Double
                val renderPosZ = declaredFieldZ.get(renderManager) as Double

                // POSITION
                val posX = player.posX - renderPosX
                val posY = player.posY - renderPosY
                val posZ = player.posZ - renderPosZ

                val expandX = 0.375
                val expandYUp = 2.0

                val startX = posX - expandX
                val startY = posY/* - 0.5*/
                val startZ = posZ - expandX

                val endX = posX + expandX
                val endY = posY/* + 0.5*/ + expandYUp
                val endZ = posZ + expandX

                // axisAlignedBBInstance
                val axisAlignedBBInstance =
                    axisAlignedBBInstanceConstructor.newInstance(startX, startY, startZ, endX, endY, endZ)

                // RenderGlobal
                /*val func_181561_a = Fields.func_181561_a

                println("Fields.func_181561_a = ${Fields.func_181561_a}")*/


/*
                // colour (Double)
                var r: Double = 1.0 as Double
                var g: Double = 1.0 as Double
                var b: Double = 1.0 as Double
                var a: Double = 1.0 as Double

                // colour (Float)
                var rf: Float = 1f
                var gf: Float = 1f
                var bf: Float = 0f
                var af: Float = .5f

                // colour (Int)
                var ri: Int = 255
                var gi: Int = 255
                var bi: Int = 0
                var ai: Int = 50
*/

                // colour (dynamic)
                val (rid, gid, bid) = getColour(
                    Triple(mySelf!!.posX, mySelf.posY, mySelf.posZ),
                    Triple(player.posX, player.posY, player.posZ)
                )
                val aid = 120

                // Invoking
                pushMetrics.invokeStatic()

                glBlendFunc.invokeStatic(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA/*, 1, 0*/)
//                glDisable.invokeStatic(GL_LIGHTING)
                glEnabled.invokeStatic(GL_BLEND)
                glLineWidth.invokeStatic(2f)
                glDisable.invokeStatic(GL_TEXTURE_2D)
                if (`ESP$Command$Settings`.throughWalls) {
                    glDisable.invokeStatic(GL_DEPTH_TEST)
                }
                glDepthMask.invokeStatic(false)
//                glColor4d.invokeStatic(r, g, b, a)
//                glColor4f.invokeStatic(rf, gf, bf, af)
//                func_181561_a.invokeStatic(axisAlignedBBInstance)
                /* func_181563_a.invokeStatic - axisAlignedBB R G B A  and rgba is int*/
//                func_181563_a.invokeStatic(axisAlignedBBInstance, ri, gi, bi, ai)
                func_181563_a.invokeStatic(axisAlignedBBInstance, rid, gid, bid, aid)
//                println("rid = $rid | gid = $gid | bid = $bid | aid = $aid")
//                glEnabled.invokeStatic(GL_LIGHTING)
                glDisable.invokeStatic(GL_BLEND)
                glEnabled.invokeStatic(GL_TEXTURE_2D)
                glEnabled.invokeStatic(GL_DEPTH_TEST)
                glDepthMask.invokeStatic(true)

                popMetrics.invokeStatic()
            }

//            println("Rendering entities")


            /*renderGlobal.methods.filter {
                // if method's argument is AxisAlignedBB return true

            }*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun onRenderNew(): Unit {
        if (LauncherMain.INSTANCE.classLoader == null) {
            LauncherMain.INSTANCE.classLoader = MessageEventClassFinder.classLoader
            LauncherMain.INSTANCE.init2()
        }

        if (LauncherMain.INSTANCE.classLoader == null) {
            return
        }
        if (!isInit) {// Minecraft
            minecraftClass =
                LauncherMain.INSTANCE.classLoader.loadClass(minecraftClassPatchName.replace('/', '.'))
            mc = minecraftClass.getDeclaredMethod(Fields.MINECRAFT_GET_MINECRAFT_METHOD_NAME).invokeStatic()!!
            // get RenderManager
            renderManagerClass =
                LauncherMain.INSTANCE.classLoader.loadClass(renderManagerClassPatchName.replace('/', '.'))
            renderManager =
                minecraftClass.getDeclaredMethod(Fields.MINECRAFT_GET_RENDER_MANAGER_METHOD_NAME).invoke(
                    mc
                )
            // fields' name
            val fX = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[0].name
            val fY = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[1].name
            val fZ = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[2].name
            // fields' value
            declaredFieldX = renderManagerClass.getDeclaredField(fX)
            declaredFieldY = renderManagerClass.getDeclaredField(fY)
            declaredFieldZ = renderManagerClass.getDeclaredField(fZ)

            gl11 = LauncherMain.INSTANCE.classLoader.loadClass("org.lwjgl.opengl.GL11")

            // GL11 Methods
            glBlendFunc =
                gl11.getMethod("glBlendFunc", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            glLineWidth = gl11.getMethod("glLineWidth", Float::class.javaPrimitiveType)
            glEnabled = gl11.getMethod("glEnable", Int::class.javaPrimitiveType)
            glDisable = gl11.getMethod("glDisable", Int::class.javaPrimitiveType)
            glDepthMask = gl11.getMethod("glDepthMask", Boolean::class.javaPrimitiveType)
            glColor4d = gl11.getMethod(
                "glColor4d",
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )
            glColor4f = gl11.getMethod(
                "glColor4f",
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType
            )
            glBegin = gl11.getMethod("glBegin", Int::class.javaPrimitiveType)
            glEnd = gl11.getMethod("glEnd")
            glVertex3d = gl11.getMethod(
                "glVertex3d",
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )

            // GL11 Fields
            GL_SRC_ALPHA = gl11.getField("GL_SRC_ALPHA").getInt(null) // 770
            GL_ONE_MINUS_SRC_ALPHA = gl11.getField("GL_ONE_MINUS_SRC_ALPHA").getInt(null) // 771
            GL_BLEND = gl11.getField("GL_BLEND").get(null) as Int // 3042
            GL_TEXTURE_2D = gl11.getField("GL_TEXTURE_2D").get(null) as Int // 3553
            GL_DEPTH_TEST = gl11.getField("GL_DEPTH_TEST").get(null) as Int // 2929
            GL_LIGHTING = gl11.getField("GL_LIGHTING").get(null) as Int // 2896

            // PushMetrics & PopMetrics
            pushMetrics =
                gl11.getMethod("glPushMatrix"/*, Void::class.javaPrimitiveType*/)
            popMetrics =
                gl11.getMethod("glPopMatrix"/*, Void::class.javaPrimitiveType*/)


            val func_181561_a_ClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/RenderGlobal")
            Debug.println("func_181561_a_ClassName = $func_181561_a_ClassName")
            val func_181561_a_Class =
                LauncherMain.INSTANCE.classLoader.loadClass(func_181561_a_ClassName.replace('/', '.'))
            func_181561_a_Class.methods.forEach {
                Debug.println("func_181561_a_Class.methods.forEach = ${it.name} | params = ${Arrays.toString(it.parameterTypes)} | returnType = ${it.returnType}")
            }
            func_181561_a_Class.methods.find { it.name == Fields.func_181561_a_name }?.let {
                func_181561_a = it
                Debug.println("Set func_181561_a = ${it.name}")

                axisAlignedBBInstanceConstructor = it.parameterTypes[0].constructors.first()
            }

            val func_181563_a_ClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/RenderGlobal")
            Debug.println("func_181563_a_ClassName = $func_181563_a_ClassName")
            val func_181563_a_Class =
                LauncherMain.INSTANCE.classLoader.loadClass(func_181563_a_ClassName.replace('/', '.'))
            func_181563_a_Class.methods.forEach {
                Debug.println("func_181563_a_Class.methods.forEach = ${it.name} | params = ${Arrays.toString(it.parameterTypes)} | returnType = ${it.returnType}")
            }
            func_181563_a_Class.methods.find { it.name == Fields.func_181563_a_name }?.let {
                func_181563_a = it
                Debug.println("Set func_181563_a = ${it.name}")
            }

            isInit = true
        }

        if (!`ESP$Command`.enabled) {
            return
        }

        val mode = `ESP$Command$Settings$Colour`.mode
        var red = `ESP$Command$Settings$Colour`.red
        var green = `ESP$Command$Settings$Colour`.green
        var blue = `ESP$Command$Settings$Colour`.blue

        try {
            var mySelf: PlayerInfo? = null
            for ((count, player) in LauncherMain.INSTANCE.playerEntitiesListInfo.withIndex()) {
                if (count == 0) {
                    mySelf = player
                    continue
                }

                var isBot = false

                if (JoinLeaveServerEventListener.isHypixel) {
                    if (`ESP$Command$Settings`.antiBotHypixel) {
                        /*if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
                            isBot = true
                        }*/
                        if (player.isBot()) {
                            isBot = true
                        }
                    }
                } else {
                    if (`ESP$Command$Settings`.antiBot) {
                        /*if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
//                        continue
                            isBot = true
                        }*/
                        if (player.isBot()) {
                            isBot = true
                        }
                    }
                }

                if (isBot) {
                    continue
                }

                val renderPosX = declaredFieldX.get(renderManager) as Double
                val renderPosY = declaredFieldY.get(renderManager) as Double
                val renderPosZ = declaredFieldZ.get(renderManager) as Double

                // POSITION
                val posX = player.posX - renderPosX
                val posY = player.posY - renderPosY
                val posZ = player.posZ - renderPosZ

                val expandX = 0.375
                val expandYUp = 2.0

                val startX = posX - expandX
                val startY = posY/* - 0.5*/
                val startZ = posZ - expandX

                val endX = posX + expandX
                val endY = posY/* + 0.5*/ + expandYUp
                val endZ = posZ + expandX

                // axisAlignedBBInstance
                val axisAlignedBBInstance =
                    axisAlignedBBInstanceConstructor.newInstance(startX, startY, startZ, endX, endY, endZ)

                // colour (dynamic)
                /*val (rid, gid, bid) = getColour(
                    Triple(mySelf!!.posX, mySelf.posY, mySelf.posZ),
                    Triple(player.posX, player.posY, player.posZ)
                )
                val aid = 120*/
                if (mode == ColourMode.DYNAMIC) {
                    val colour = getColour(
                        Triple(mySelf!!.posX, mySelf.posY, mySelf.posZ),
                        Triple(player.posX, player.posY, player.posZ)
                    )
                    red = colour.first
                    green = colour.second
                    blue = colour.third
                }
                val aid = 120

                // Invoking
                GL11.glPushMatrix()
                GL11.glBlendFunc(GL11.GL_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
                GL11.glEnable(GL11.GL_BLEND)
                GL11.glLineWidth(2f)
                GL11.glDisable(GL11.GL_TEXTURE_2D)
                if (`ESP$Command$Settings`.throughWalls) {
                    GL11.glDisable(GL11.GL_DEPTH_TEST)
                }
                GL11.glDepthMask(false)
//                func_181563_a.invokeStatic(axisAlignedBBInstance, rid, gid, bid, aid)
                func_181563_a.invokeStatic(axisAlignedBBInstance, red, green, blue, aid)
                GL11.glDisable(GL11.GL_BLEND)
                GL11.glEnable(GL11.GL_TEXTURE_2D)
                GL11.glEnable(GL11.GL_DEPTH_TEST)
                GL11.glDepthMask(true)
                GL11.glPopMatrix()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun onRender2(): Unit {
//        println("ESP onRender")
        if (LauncherMain.INSTANCE.classLoader == null) {
            LauncherMain.INSTANCE.classLoader = MessageEventClassFinder.classLoader
            LauncherMain.INSTANCE.init2()
        }

        if (LauncherMain.INSTANCE.classLoader == null) {
            return
        }
        if (!isInit) {// Minecraft
            minecraftClass =
                LauncherMain.INSTANCE.classLoader.loadClass(minecraftClassPatchName.replace('/', '.'))
            mc = minecraftClass.getDeclaredMethod(Fields.MINECRAFT_GET_MINECRAFT_METHOD_NAME).invokeStatic()!!
            // get RenderManager
            renderManagerClass =
                LauncherMain.INSTANCE.classLoader.loadClass(renderManagerClassPatchName.replace('/', '.'))
            renderManager =
                minecraftClass.getDeclaredMethod(Fields.MINECRAFT_GET_RENDER_MANAGER_METHOD_NAME).invoke(
                    mc
                )
            // fields' name
            val fX = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[0].name
            val fY = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[1].name
            val fZ = Fields.RENDER_MANAGER_POS_X_Y_Z_FIELDS[2].name
            // fields' value
            declaredFieldX = renderManagerClass.getDeclaredField(fX)
            declaredFieldY = renderManagerClass.getDeclaredField(fY)
            declaredFieldZ = renderManagerClass.getDeclaredField(fZ)

//                val gl11 = Class.forName("org.lwjgl.opengl.GL11")
            gl11 = LauncherMain.INSTANCE.classLoader.loadClass("org.lwjgl.opengl.GL11")

            // GL11 Methods
            glBlendFunc =
                gl11.getMethod("glBlendFunc", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            glLineWidth = gl11.getMethod("glLineWidth", Float::class.javaPrimitiveType)
            glEnabled = gl11.getMethod("glEnable", Int::class.javaPrimitiveType)
            glDisable = gl11.getMethod("glDisable", Int::class.javaPrimitiveType)
            glDepthMask = gl11.getMethod("glDepthMask", Boolean::class.javaPrimitiveType)
            glColor4d = gl11.getMethod(
                "glColor4d",
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )
            glColor4f = gl11.getMethod(
                "glColor4f",
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType
            )
            glBegin = gl11.getMethod("glBegin", Int::class.javaPrimitiveType)
            glEnd = gl11.getMethod("glEnd")
            glVertex3d = gl11.getMethod(
                "glVertex3d",
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )
            glBegin = gl11.getMethod("glBegin", Int::class.javaPrimitiveType)
            glEnd = gl11.getMethod("glEnd")
            glVertex3d = gl11.getMethod(
                "glVertex3d",
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )

            // GL11 Fields
            GL_SRC_ALPHA = gl11.getField("GL_SRC_ALPHA").getInt(null) // 770
            GL_ONE_MINUS_SRC_ALPHA = gl11.getField("GL_ONE_MINUS_SRC_ALPHA").getInt(null) // 771
            GL_BLEND = gl11.getField("GL_BLEND").get(null) as Int // 3042
            GL_TEXTURE_2D = gl11.getField("GL_TEXTURE_2D").get(null) as Int // 3553
            GL_DEPTH_TEST = gl11.getField("GL_DEPTH_TEST").get(null) as Int // 2929
            GL_LIGHTING = gl11.getField("GL_LIGHTING").get(null) as Int // 2896

            /*println("GL_BLEND = $GL_BLEND")
            println("GL_TEXTURE_2D = $GL_TEXTURE_2D")
            println("GL_DEPTH_TEST = $GL_DEPTH_TEST")*/

            // AxisAlignedBB

//            println("Init success")

            // PushMetrics & PopMetrics
            pushMetrics =
                gl11.getMethod("glPushMatrix"/*, Void::class.javaPrimitiveType*/)
            popMetrics =
                gl11.getMethod("glPopMatrix"/*, Void::class.javaPrimitiveType*/)


            val func_181561_a_ClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/RenderGlobal")
            Debug.println("func_181561_a_ClassName = $func_181561_a_ClassName")
            val func_181561_a_Class =
                LauncherMain.INSTANCE.classLoader.loadClass(func_181561_a_ClassName.replace('/', '.'))
            func_181561_a_Class.methods.forEach {
                Debug.println("func_181561_a_Class.methods.forEach = ${it.name} | params = ${Arrays.toString(it.parameterTypes)} | returnType = ${it.returnType}")
            }
//            func_181561_a = func_181561_a_Class.getMethod(Fields.func_181561_a_name, Fields.AXIS_ALIGNED_BB_CLASS)
            func_181561_a_Class.methods.find { it.name == Fields.func_181561_a_name }?.let {
                func_181561_a = it
                Debug.println("Set func_181561_a = ${it.name}")

                axisAlignedBBInstanceConstructor = it.parameterTypes[0].constructors.first()
            }

            val func_181563_a_ClassName =
                LunarClassFinderMain.LunarClassFinderMAIN.getPatchName("net/minecraft/client/renderer/RenderGlobal")
            Debug.println("func_181563_a_ClassName = $func_181563_a_ClassName")
            val func_181563_a_Class =
                LauncherMain.INSTANCE.classLoader.loadClass(func_181563_a_ClassName.replace('/', '.'))
            func_181563_a_Class.methods.forEach {
                Debug.println("func_181563_a_Class.methods.forEach = ${it.name} | params = ${Arrays.toString(it.parameterTypes)} | returnType = ${it.returnType}")
            }
            func_181563_a_Class.methods.find { it.name == Fields.func_181563_a_name }?.let {
                func_181563_a = it
                Debug.println("Set func_181563_a = ${it.name}")
            }

            /*axisAlignedBBInstanceConstructor = Fields.AXIS_ALIGNED_BB_CLASS.getDeclaredConstructor(
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType,
                Double::class.javaPrimitiveType
            )*/




            isInit = true
        }

        if (!`ESP$Command`.enabled) {
            return
        }

        val mode = `ESP$Command$Settings$Colour`.mode
        var red = `ESP$Command$Settings$Colour`.red
        var green = `ESP$Command$Settings$Colour`.green
        var blue = `ESP$Command$Settings$Colour`.blue

//        return
//        println("EntityRenderer")
        try {
            var mySelf: PlayerInfo? = null
            for ((count, player) in LauncherMain.INSTANCE.playerEntitiesListInfo.withIndex()) {
                if (count == 0) {
                    mySelf = player
                    continue
                }

                var isBot = false

                if (JoinLeaveServerEventListener.isHypixel) {
                    if (`ESP$Command$Settings`.antiBotHypixel) {
                        /*if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
                            isBot = true
                        }*/
                        if (player.isBot()) {
                            isBot = true
                        }
                    }
                } else {
                    if (`ESP$Command$Settings`.antiBot) {
                        /*if (`ESP$Command$Settings$AntiBot`.isBot(player.name)) {
//                        continue
                            isBot = true
                        }*/
                        if (player.isBot()) {
                            isBot = true
                        }
                    }
                }

                if (isBot) {
                    continue
                }

//                println("Player: ${player.name}")

                val renderPosX = declaredFieldX.get(renderManager) as Double
                val renderPosY = declaredFieldY.get(renderManager) as Double
                val renderPosZ = declaredFieldZ.get(renderManager) as Double

                // POSITION
                val posX = player.posX - renderPosX
                val posY = player.posY - renderPosY
                val posZ = player.posZ - renderPosZ

                val expandX = 0.375
                val expandYUp = 2.0

                val startX = posX - expandX
                val startY = posY/* - 0.5*/
                val startZ = posZ - expandX

                val endX = posX + expandX
                val endY = posY/* + 0.5*/ + expandYUp
                val endZ = posZ + expandX

                // axisAlignedBBInstance
                val axisAlignedBBInstance =
                    axisAlignedBBInstanceConstructor.newInstance(startX, startY, startZ, endX, endY, endZ)

                // RenderGlobal
                /*val func_181561_a = Fields.func_181561_a

                println("Fields.func_181561_a = ${Fields.func_181561_a}")*/


                /*
                                // colour (Double)
                                var r: Double = 1.0 as Double
                                var g: Double = 1.0 as Double
                                var b: Double = 1.0 as Double
                                var a: Double = 1.0 as Double

                                // colour (Float)
                                var rf: Float = 1f
                                var gf: Float = 1f
                                var bf: Float = 0f
                                var af: Float = .5f

                                // colour (Int)
                                var ri: Int = 255
                                var gi: Int = 255
                                var bi: Int = 0
                                var ai: Int = 50
                */

                // colour (dynamic)
                if (mode == ColourMode.DYNAMIC) {
                    val colour = getColour(
                        Triple(mySelf!!.posX, mySelf.posY, mySelf.posZ),
                        Triple(player.posX, player.posY, player.posZ)
                    )
                    red = colour.first
                    green = colour.second
                    blue = colour.third
                }
                val aid = 120

                // Invoking
                pushMetrics.invokeStatic()

                glBlendFunc.invokeStatic(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
                glEnabled.invokeStatic(GL_BLEND)
                glLineWidth.invokeStatic(2f)
                glDisable.invokeStatic(GL_TEXTURE_2D)
                if (`ESP$Command$Settings`.throughWalls) {
                    glDisable.invokeStatic(GL_DEPTH_TEST)
                }
                glDepthMask.invokeStatic(false)
                func_181563_a.invokeStatic(axisAlignedBBInstance, red, green, blue, aid)
                glDisable.invokeStatic(GL_BLEND)
                glEnabled.invokeStatic(GL_TEXTURE_2D)
                glEnabled.invokeStatic(GL_DEPTH_TEST)
                glDepthMask.invokeStatic(true)

                popMetrics.invokeStatic()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // method that get the distance and then return the colour r g b Triple<> in Int in gradient | near = red | far = green
    fun getColour(pos1: Triple<Double, Double, Double>, pos2: Triple<Double, Double, Double>): Triple<Int, Int, Int> {
        val distance = PlayerWatchedThread.calculateDistance(pos1.first, pos1.second, pos1.third, pos2.first, pos2.second, pos2.third)
        /*val r = (1 - distance / 40).toInt()
        val g = (distance / 40).toInt()
        val b = 0*/
        /*val r = ((1 - distance / 40) * 255).toInt()
        val g = ((distance / 40) * 255).toInt()
        val b = 0*/
        val r = ((1 - distance / 40) * 255).toInt().coerceAtMost(255).coerceAtLeast(0)
        val g = ((distance / 40) * 255).toInt().coerceAtMost(255).coerceAtLeast(0)
        val b = 0

        return Triple(r, g, b)
    }
}