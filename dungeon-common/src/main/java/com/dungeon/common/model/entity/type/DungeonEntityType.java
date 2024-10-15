package com.dungeon.common.model.entity.type;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;

public abstract class DungeonEntityType {

    public abstract int getMinimunWave();

    public abstract EntityType getEntityType();

    public abstract String getDisplayName();

    public String getSimpleName() {
        return getDisplayName().substring(2);
    }
}
