package com.dungeon.common.factory.stats;

import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.dungeon.common.model.entity.stats.impl.AllayDungeonEntityStats;
import com.dungeon.common.model.entity.stats.impl.TotemDungeonEntityStats;
import com.dungeon.common.model.entity.stats.impl.ZombieDungeonEntityStats;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DungeonEntityStatsFactory {

    private static final Map<EntityType, DungeonEntityStats> cache = new HashMap<>() {
        {
            put(EntityTypes.END_CRYSTAL, new TotemDungeonEntityStats());
            put(EntityTypes.ZOMBIE, new ZombieDungeonEntityStats());
            put(EntityTypes.ALLAY, new AllayDungeonEntityStats());
        }
    };

    /**
     * Retrieves the {@link DungeonEntityStats} for the given {@link EntityType} from the cache.
     *
     * <p>If the entity type exists in the cache, it returns the associated {@link DungeonEntityStats}.
     * If the entity type is not found, this method will return {@code null}.</p>
     *
     * @param entityType The {@link EntityType} for which the stats need to be retrieved.
     * @return The {@link DungeonEntityStats} corresponding to the provided entity type, or {@code null} if not found.
     */
    public static DungeonEntityStats getStats(EntityType entityType) {
        return cache.get(entityType);
    }
}
