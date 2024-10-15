package com.dungeon.common.model.entity.stats;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;

public abstract class DungeonEntityStats {

    public abstract EntityType getEntityType();

    public abstract double getDamage();

    public abstract double getMaxHealth();

    public abstract int getAttackRadius();

    public abstract int getHitDelay();

    public abstract boolean canJump();
}
