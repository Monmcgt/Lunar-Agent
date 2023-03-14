package me.monmcgt.code.lunarbuiltinlauncher.entities;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
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

    public List<PlayerInfo> lookingAt(PlayerInfo[] players) {
        List<PlayerInfo> lookingAt = new ArrayList<>();
        for (PlayerInfo player : players) {
            if (this.isLookingAt(player)) {
                lookingAt.add(player);
            }
        }
        return lookingAt;
    }

    public boolean isLookingAt(PlayerInfo targetPlayer) {
        // Get the position and rotation of the source player
        double sourceX = this.posX;
        double sourceY = this.posY + this.eyeHeight;
        double sourceZ = this.posZ;
        double sourceYaw = Math.toRadians(this.yaw);
        double sourcePitch = Math.toRadians(this.pitch);

        // Calculate the direction that the source player is facing
        double directionX = Math.cos(sourceYaw) * Math.cos(sourcePitch);
        double directionY = Math.sin(sourcePitch);
        double directionZ = Math.sin(sourceYaw) * Math.cos(sourcePitch);

        // Get the bounding box of the target player
        BoundingBox targetBoundingBox = targetPlayer.boundingBox;

        // Calculate the center of the target player's bounding box
        double targetCenterX = (targetBoundingBox.minX + targetBoundingBox.maxX) / 2;
        double targetCenterY = (targetBoundingBox.minY + targetBoundingBox.maxY) / 2;
        double targetCenterZ = (targetBoundingBox.minZ + targetBoundingBox.maxZ) / 2;

        // Calculate the vector from the source player's position to the center of the target player's bounding box
        double vectorX = targetCenterX - sourceX;
        double vectorY = targetCenterY - sourceY;
        double vectorZ = targetCenterZ - sourceZ;

        // Calculate the dot product of the direction and the vector
        double dotProduct = directionX * vectorX + directionY * vectorY + directionZ * vectorZ;

        // Calculate the magnitude of the vector from the source player's position to the center of the target player's bounding box
        double vectorMagnitude = Math.sqrt(vectorX * vectorX + vectorY * vectorY + vectorZ * vectorZ);

        // Calculate the cosine of the angle between the direction and the vector
        double cosine = dotProduct / vectorMagnitude;

        // Calculate the maximum cosine of the angle between the direction and the edges of the target player's bounding box
        double halfWidthX = (targetBoundingBox.maxX - targetBoundingBox.minX) / 2;
        double halfWidthY = (targetBoundingBox.maxY - targetBoundingBox.minY) / 2;
        double halfWidthZ = (targetBoundingBox.maxZ - targetBoundingBox.minZ) / 2;
        double maxCosineX = halfWidthX / vectorMagnitude;
        double maxCosineY = halfWidthY / vectorMagnitude;
        double maxCosineZ = halfWidthZ / vectorMagnitude;
        double maxCosine = Math.max(Math.max(maxCosineX, maxCosineY), maxCosineZ);

        // Check if the source player is looking at any part of the target player's bounding box
        return cosine >= maxCosine;
    }
}
