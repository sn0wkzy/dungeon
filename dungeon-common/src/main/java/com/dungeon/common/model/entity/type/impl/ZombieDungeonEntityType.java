package com.dungeon.common.model.entity.type.impl;

import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

public class ZombieDungeonEntityType extends DungeonEntityType {
    @Override
    public int getMinimunWave() {
        return 0;
    }

    @Override
    public EntityType getEntityType() {
        return EntityTypes.ZOMBIE;
    }

    @Override
    public String getDisplayName() {
        return "§2Zumbi";
    }
}
