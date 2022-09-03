package me.monmcgt.code.helpers;

import java.lang.reflect.Field;

public class ValueSetter {
    public static ValueSetter INSTANCE;

    public ValueSetter() {
        INSTANCE = this;
    }

    public void set(String fieldName, String value) {
        try {
            Class<?> clazz = Class.forName("me.monmcgt.code.lunarbuiltinlauncher.Data");
            Field field = clazz.getDeclaredField(fieldName);
            field.set(null, value);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
