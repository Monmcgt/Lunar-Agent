package me.monmcgt.code.wrappers.net.minecraft.client.renderer.net.minecraft.client.entity;

import me.monmcgt.code.transformers.Transformer$1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityPlayerSP {
    public static Class<?> realClass;
    public Object realInstance;

    public EntityPlayerSP(Object entityPlayerSPInstance) {
        this.realInstance = entityPlayerSPInstance;
    }

    public float getEyeHeight() {
        // bridge$getEyeHeight
        try {
            return (float) Methods.bridge$getEyeHeight.invoke(realInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        realClass = Transformer$1.classes.get("net/minecraft/client/entity/EntityPlayerSP");
    }

    private static class Methods {
        public static final String getEyeHeight = "bridge$getEyeHeight";
        public static Method bridge$getEyeHeight;

        static {
            try {
                bridge$getEyeHeight = realClass.getMethod(getEyeHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
