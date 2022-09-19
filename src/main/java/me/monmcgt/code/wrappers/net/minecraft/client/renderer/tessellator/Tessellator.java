package me.monmcgt.code.wrappers.net.minecraft.client.renderer.tessellator;

import me.monmcgt.code.finders.RenderUtilsHelperFinder;
import me.monmcgt.code.lunarbuiltinlauncher.LauncherMain;
import me.monmcgt.code.transformers.Transformer$1;
import me.monmcgt.code.wrappers.net.minecraft.client.renderer.worldrenderer.WorldRenderer;

import java.lang.reflect.Method;

public class Tessellator {
    public static Method Tessellator$getInstance;
    public static Method Tessellator$getWorldRenderer;
    public static Method Tessellator$draw;

    private static Tessellator instance;

    static {
        // LauncherMain.INSTANCE.classLoader.loadClass()
        try {
            Class<?> tessellatorClass = LauncherMain.INSTANCE.classLoader.loadClass(Transformer$1.classes.get(RenderUtilsHelperFinder.INSTANCE.getTessellatorMCPMapping()).getName().replace('/', '.'));
            Tessellator$getInstance = tessellatorClass.getMethod(Tessellator$Data.Tessellator$getInstanceString);
            Tessellator$getWorldRenderer = tessellatorClass.getMethod(Tessellator$Data.Tessellator$getWorldRendererString);
            Tessellator$draw = tessellatorClass.getMethod(Tessellator$Data.Tessellator$drawString);

            instance = new Tessellator(getRealInstance());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getRealInstance() {
        try {
            return Tessellator$getInstance.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Tessellator getInstance() {
        return instance;
    }

    public Object realInstance;

    public Tessellator() {
        this.realInstance = getRealInstance();
    }

    public Tessellator(Object tessellatorInstance) {
        this.realInstance = tessellatorInstance;
    }

    public WorldRenderer getWorldRenderer() {
        try {
            return new WorldRenderer(Tessellator$getWorldRenderer.invoke(realInstance));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
