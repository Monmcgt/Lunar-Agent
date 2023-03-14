package me.monmcgt.code.lunarbridgewrapper;

import java.util.Optional;

public class EntityPlayer {
    private Object object;

    /*
    public Optional bridge$getLastAttacker();

    public int bridge$getLastAttackerTime();

    public int bridge$getHurtTime();

    public int bridge$getDeathTime();

    public boolean bridge$isPotionActive(IllIlIIllIIlllIIllIlIlIII var1);

    public lunar.ai.IIllIIIIIllIIIIlIIIlIIIll bridge$getActivePotionEffect(IllIlIIllIIlllIIllIlIlIII var1);

    public List bridge$getActivePotionEffects();

    public boolean bridge$isOnLadder();

    public boolean bridge$isInWater();

    public int bridge$getArmSwingAnimationEnd();

    public float bridge$getRenderYawOffset();

    public float bridge$getPreviousRotationYawOffset();

    public void bridge$setRenderYawOffset(float var1);

    public float bridge$getRotationYawHead();

    public void bridge$setRotationYawHead(float var1);

    public float bridge$getPrevRotationYawHead();

    public void bridge$setPrevRotationYawHead(float var1);

    public float bridge$getPrevRenderYawOffset();

    public void IIIllIIlIIIIllIlIlIIIIlII(float var1);

    public IIlIllIllIIIIllIlIIlIlIll bridge$getEquipmentInSlot(int var1);

    public float bridge$getHealth();

    public boolean bridge$isPlayerSleeping();

    public float bridge$getMoveForward();

    public IIlIllIllIIIIllIlIIlIlIll bridge$getHeldItem();

    public boolean bridge$shouldAlwaysRenderNametag();

    public void bridge$setAlwaysRenderNameTag(boolean var1);

    public String bridge$getDisplayName();

    default public boolean lIllllIIIlIIIIlIlIIIIlllI() {
        return false;
    }

    default public boolean llIlllIllIIlIlIIllIlIIIII() {
        return false;
    }
    */

    // generate field only primitive types or java types
    // if not, generate but comment out

    public Optional lastAttacker; // bridge$getLastAttacker()
    public int lastAttackerTime; // bridge$getLastAttackerTime()
    public int hurtTime; // bridge$getHurtTime()
    public int deathTime; // bridge$getDeathTime()
    // public boolean potionActive; // bridge$isPotionActive(IllIlIIllIIlllIIllIlIlIII var1)
    // public lunar.ai.IIllIIIIIllIIIIlIIIlIIIll activePotionEffect; // bridge$getActivePotionEffect(IllIlIIllIIlllIIllIlIlIII var1)
    // public List activePotionEffects; // bridge$getActivePotionEffects()
    public boolean onLadder; // bridge$isOnLadder()
    public boolean inWater; // bridge$isInWater()
    public int armSwingAnimationEnd; // bridge$getArmSwingAnimationEnd()
    public float renderYawOffset; // bridge$getRenderYawOffset()
    public float previousRotationYawOffset; // bridge$getPreviousRotationYawOffset()
    public float rotationYawHead; // bridge$getRotationYawHead()
    public float prevRotationYawHead; // bridge$getPrevRotationYawHead()
    public float prevRenderYawOffset; // bridge$getPrevRenderYawOffset()
    // public IIlIllIllIIIIllIlIIlIlIll equipmentInSlot; // bridge$getEquipmentInSlot(int var1)
    public float health; // bridge$getHealth()
    public boolean isPlayerSleeping; // bridge$isPlayerSleeping()
    public float moveForward; // bridge$getMoveForward()
    // public IIlIllIllIIIIllIlIIlIlIll heldItem; // bridge$getHeldItem()
    public boolean shouldAlwaysRenderNametag; // bridge$shouldAlwaysRenderNametag()
    public String displayName; // bridge$getDisplayName()

    public EntityPlayer(Optional lastAttacker, int lastAttackerTime, int hurtTime, int deathTime, boolean onLadder, boolean inWater, int armSwingAnimationEnd, float renderYawOffset, float previousRotationYawOffset, float rotationYawHead, float prevRotationYawHead, float prevRenderYawOffset, float health, boolean isPlayerSleeping, float moveForward, boolean shouldAlwaysRenderNametag, String displayName) {
        this.lastAttacker = lastAttacker;
        this.lastAttackerTime = lastAttackerTime;
        this.hurtTime = hurtTime;
        this.deathTime = deathTime;
        this.onLadder = onLadder;
        this.inWater = inWater;
        this.armSwingAnimationEnd = armSwingAnimationEnd;
        this.renderYawOffset = renderYawOffset;
        this.previousRotationYawOffset = previousRotationYawOffset;
        this.rotationYawHead = rotationYawHead;
        this.prevRotationYawHead = prevRotationYawHead;
        this.prevRenderYawOffset = prevRenderYawOffset;
        this.health = health;
        this.isPlayerSleeping = isPlayerSleeping;
        this.moveForward = moveForward;
        this.shouldAlwaysRenderNametag = shouldAlwaysRenderNametag;
        this.displayName = displayName;
    }

    public EntityPlayer(Object object, Optional lastAttacker, int lastAttackerTime, int hurtTime, int deathTime, boolean onLadder, boolean inWater, int armSwingAnimationEnd, float renderYawOffset, float previousRotationYawOffset, float rotationYawHead, float prevRotationYawHead, float prevRenderYawOffset, float health, boolean isPlayerSleeping, float moveForward, boolean shouldAlwaysRenderNametag, String displayName) {
        this(lastAttacker, lastAttackerTime, hurtTime, deathTime, onLadder, inWater, armSwingAnimationEnd, renderYawOffset, previousRotationYawOffset, rotationYawHead, prevRotationYawHead, prevRenderYawOffset, health, isPlayerSleeping, moveForward, shouldAlwaysRenderNametag, displayName);
        this.object = object;
    }
}
