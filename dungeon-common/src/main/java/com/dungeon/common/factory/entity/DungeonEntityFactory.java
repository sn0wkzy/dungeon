package com.dungeon.common.factory.entity;

import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.entity.handler.impl.AllayDungeonEntityHandler;
import com.dungeon.common.model.entity.handler.impl.TotemDungeonEntityHandler;
import com.dungeon.common.model.entity.handler.impl.ZombieDungeonEntityHandler;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DungeonEntityFactory {

    private static final Map<EntityType, DungeonEntityHandler> cache = new HashMap<>() {
        {
            put(EntityTypes.END_CRYSTAL, new TotemDungeonEntityHandler());
            put(EntityTypes.ZOMBIE, new ZombieDungeonEntityHandler());
            put(EntityTypes.ALLAY, new AllayDungeonEntityHandler());
        }
    };

    /**
     * Retrieves the {@link DungeonEntityHandler} associated with the specified {@link EntityType}.
     * If the entity type is not present in the cache, this method will return null.
     *
     * @param entityType the {@link EntityType} for which the handler is requested
     * @return the corresponding {@link DungeonEntityHandler}, or null if not found
     */
    public static DungeonEntityHandler getEntity(EntityType entityType) {
        return cache.get(entityType);
    }
}
