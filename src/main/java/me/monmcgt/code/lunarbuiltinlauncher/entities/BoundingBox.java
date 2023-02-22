package me.monmcgt.code.lunarbuiltinlauncher.entities;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class BoundingBox {
    private Object object;

    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public BoundingBox(Object object, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this(minX, minY, minZ, maxX, maxY, maxZ);
        this.object = object;
    }

    /*public boolean intersectsWith(BoundingBox other) {
        return this.minX < other.maxX && this.maxX > other.minX && this.minY < other.maxY && this.maxY > other.minY && this.minZ < other.maxZ && this.maxZ > other.minZ;
    }*/

    @SneakyThrows
    public boolean intersectsWith(Object other) {
        return (boolean) object.getClass().getMethod("intersectsWith", other.getClass()).invoke(object, other);
    }
}
