package me.monmcgt.code.lunarbuiltinlauncher.entities;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

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
    public boolean isSneaking;
    public boolean isRiding;
    public int ticksExisted;
    public double previousPosX;
    public double previousPosY;
    public double previousPosZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public double previousRotationYaw;
    public double previousRotationPitch;
    public UUID uniqueID;
    public BoundingBox boundingBox;
    public boolean isOnGround;
    public int entityId;
    public LookAngle lookAngle;
    public int dimension;
    public float eyeHeight;
    public float fallDistance;
    public double lastTickX;
    public double lastTickY;
    public double lastTickZ;
    public boolean isฺฺBurning;
    public float width;
    public boolean isCollidedHorizontally;
    public boolean isDead;
    public boolean isVisiblyCrouching;

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

    public PlayerInfo(String name, double posX, double posY, double posZ, double yaw, double pitch, boolean invisible, boolean invisibleToMyself, boolean isSneaking, boolean isRiding, int ticksExisted, double previousPosX, double previousPosY, double previousPosZ, double motionX, double motionY, double motionZ, double previousRotationYaw, double previousRotationPitch, UUID uniqueID, BoundingBox boundingBox, boolean isOnGround, int entityId, LookAngle lookAngle, int dimension, float eyeHeight, float fallDistance, double lastTickX, double lastTickY, double lastTickZ, boolean isฺฺBurning, float width, boolean isCollidedHorizontally, boolean isDead, boolean isVisiblyCrouching) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.invisible = invisible;
        this.invisibleToMyself = invisibleToMyself;
        this.isSneaking = isSneaking;
        this.isRiding = isRiding;
        this.ticksExisted = ticksExisted;
        this.previousPosX = previousPosX;
        this.previousPosY = previousPosY;
        this.previousPosZ = previousPosZ;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.previousRotationYaw = previousRotationYaw;
        this.previousRotationPitch = previousRotationPitch;
        this.uniqueID = uniqueID;
        this.boundingBox = boundingBox;
        this.isOnGround = isOnGround;
        this.entityId = entityId;
        this.lookAngle = lookAngle;
        this.dimension = dimension;
        this.eyeHeight = eyeHeight;
        this.fallDistance = fallDistance;
        this.lastTickX = lastTickX;
        this.lastTickY = lastTickY;
        this.lastTickZ = lastTickZ;
        this.isฺฺBurning = isฺฺBurning;
        this.width = width;
        this.isCollidedHorizontally = isCollidedHorizontally;
        this.isDead = isDead;
        this.isVisiblyCrouching = isVisiblyCrouching;
    }
}
