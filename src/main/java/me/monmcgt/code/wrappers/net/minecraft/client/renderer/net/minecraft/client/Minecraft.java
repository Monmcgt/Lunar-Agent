package me.monmcgt.code.wrappers.net.minecraft.client.renderer.net.minecraft.client;

import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain;
import me.monmcgt.code.wrappers.net.minecraft.client.renderer.net.minecraft.client.entity.EntityPlayerSP;

public class Minecraft {
    public static EntityPlayerSP getPlayerBridge() {
        return new EntityPlayerSP(LauncherMain.INSTANCE.bridgeGetPlayer());
    }
}
