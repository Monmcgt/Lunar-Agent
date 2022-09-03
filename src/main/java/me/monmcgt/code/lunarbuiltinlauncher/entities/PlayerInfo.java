package me.monmcgt.code.lunarbuiltinlauncher.entities;

import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class PlayerInfo {
    public String name;
    public double posX;
    public double posY;
    public double posZ;
    public double yaw;
    public double pitch;
    public boolean invisible;
    public boolean invisibleToMyself;

    public PlayerInfo(String name, double posX, double posY, double posZ, double yaw, double pitch, boolean invisible, boolean invisibleToMyself) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.invisible = invisible;
        this.invisibleToMyself = invisibleToMyself;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isInvisibleToMyself() {
        return invisibleToMyself;
    }

    public void setInvisibleToMyself(boolean invisibleToMyself) {
        this.invisibleToMyself = invisibleToMyself;
    }
}
