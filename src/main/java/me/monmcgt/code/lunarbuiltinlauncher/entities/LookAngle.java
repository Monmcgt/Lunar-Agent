package me.monmcgt.code.lunarbuiltinlauncher.entities;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class LookAngle {
    private Object object;

    public double xCoord;
    public double yCoord;
    public double zCoord;
    public double lengthVector;

    public LookAngle(double xCoord, double yCoord, double zCoord, double lengthVector) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.lengthVector = lengthVector;
    }

    public LookAngle(Object object, double xCoord, double yCoord, double zCoord, double lengthVector) {
        this(xCoord, yCoord, zCoord, lengthVector);
        this.object = object;
    }

    @SneakyThrows
    public double dotProduct(Object var1) {
        return (double) object.getClass().getMethod("bridge$dotProduct", var1.getClass()).invoke(object, var1);
    }

    @SneakyThrows
    public static Object normalize(Object var1) {
        var1.getClass().getMethod("bridge$normalize").invoke(var1);
        return var1;
    }

    @SneakyThrows
    public static Object crossProduct(Object var1, Object var2) {
        var1.getClass().getMethod("bridge$crossProduct", var2.getClass()).invoke(var1, var2);
        return var1;
    }

    @SneakyThrows
    public static LookAngle migrateObject(Object object) {
        Class<?> clazz = object.getClass();
        return new LookAngle(object,
                (double) clazz.getMethod("bridge$xCoord").invoke(object),
                (double) clazz.getMethod("bridge$yCoord").invoke(object),
                (double) clazz.getMethod("bridge$zCoord").invoke(object),
                (double) clazz.getMethod("bridge$lengthVector").invoke(object));
    }
}
