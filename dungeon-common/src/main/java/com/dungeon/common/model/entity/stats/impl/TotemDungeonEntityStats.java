package com.dungeon.common.model.entity.stats.impl;

import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

public class TotemDungeonEntityStats extends DungeonEntityStats {
    @Override
    public EntityType getEntityType() {
        return EntityTypes.END_CRYSTAL;
    }

    @Override
    public double getDamage() {
        return 125;
    }

    @Override
    public double getMaxHealth() {
        return 25000.0;
    }

    @Override
    public int getAttackRadius() {
        return 20;
    }

    @Override
    public int getHitDelay() {
        return 350;
    }

    @Override
    public boolean canJump() {
        return false;
    }
}
