package com.dungeon.common.model.entity.stats.impl;

import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

public class ZombieDungeonEntityStats extends DungeonEntityStats {
    @Override
    public EntityType getEntityType() {
        return EntityTypes.ZOMBIE;
    }

    @Override
    public double getDamage() {
        return 25;
    }

    @Override
    public double getMaxHealth() {
        return 750.0;
    }

    @Override
    public int getAttackRadius() {
        return 2;
    }

    @Override
    public int getHitDelay() {
        return 750;
    }

    @Override
    public boolean canJump() {
        return false;
    }
}
