package me.monmcgt.code.util

import me.monmcgt.code.lunarclassfinder.invokeStatic
import me.monmcgt.code.modules.ESP
import me.monmcgt.code.wrappers.net.minecraft.client.renderer.net.minecraft.client.Minecraft

object RenderUtils {
    fun drawTracerLineWithHelpFromESPModule(
        x: Double,
        y: Double,
        z: Double,
        redI: Int,
        greenI: Int,
        blueI: Int,
        alpha: Float,
        lineWidth: Float
    ) {
        val red = redI / 255f
        val green = greenI / 255f
        val blue = blueI / 255f

        ESP.pushMetrics.invokeStatic()
        ESP.glEnabled.invokeStatic(ESP.GL_BLEND)
        ESP.glEnabled.invokeStatic(2848) // GL_LINE_SMOOTH
        ESP.glEnabled.invokeStatic(ESP.GL_DEPTH_TEST)
        ESP.glDisable.invokeStatic(3553) // GL_TEXTURE_2D
        ESP.glBlendFunc.invokeStatic(770, 771)
        ESP.glLineWidth.invokeStatic(lineWidth)
        ESP.glColor4f.invokeStatic(red, green, blue, alpha)
        ESP.glBegin.invokeStatic(2)
        ESP.glVertex3d.invokeStatic(0.0, 0.0 + Minecraft.getPlayerBridge().eyeHeight, 0.0)
        ESP.glVertex3d.invokeStatic(x, y, z)
        ESP.glEnd.invokeStatic()
        ESP.glDisable.invokeStatic(ESP.GL_BLEND)
        ESP.glDisable.invokeStatic(2848) // GL_LINE_SMOOTH
//        ESP.glDisable.invokeStatic(ESP.GL_DEPTH_TEST)
        ESP.glEnabled.invokeStatic(3553) // GL_TEXTURE_2D
        ESP.popMetrics.invokeStatic()
    }
}