package me.monmcgt.code.modules.hook;

import me.monmcgt.code.modules.ESP;
import me.monmcgt.code.modules.Tracers;

public class RenderHook {
    public static void onRender() {
        ESP.onRender2();
        Tracers.onRender();
    }
}
