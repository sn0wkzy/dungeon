package com.dungeon.common.model.entity.stats.impl;

import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

public class AllayDungeonEntityStats extends DungeonEntityStats {
    @Override
    public EntityType getEntityType() {
        return EntityTypes.ALLAY;
    }

    @Override
    public double getDamage() {
        return 75;
    }

    @Override
    public double getMaxHealth() {
        return 500.0;
    }

    @Override
    public int getAttackRadius() {
        return 4;
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
