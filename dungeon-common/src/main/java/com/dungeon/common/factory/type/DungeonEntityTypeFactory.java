package com.dungeon.common.factory.type;

import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.dungeon.common.model.entity.type.impl.AllayDungeonEntityType;
import com.dungeon.common.model.entity.type.impl.TotemDungeonEntityType;
import com.dungeon.common.model.entity.type.impl.ZombieDungeonEntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DungeonEntityTypeFactory {

    private static final Map<EntityType, DungeonEntityType> cache = new HashMap<>() {
        {
            put(EntityTypes.END_CRYSTAL, new TotemDungeonEntityType());
            put(EntityTypes.ZOMBIE, new ZombieDungeonEntityType());
            put(EntityTypes.ALLAY, new AllayDungeonEntityType());
        }
    };

    /**
     * Retrieves the DungeonEntityType associated with the given EntityType.
     *
     * @param entityType The EntityType for which to retrieve the corresponding DungeonEntityType.
     * @return The corresponding DungeonEntityType, or null if not found.
     */
    public static DungeonEntityType getType(EntityType entityType) {
        return cache.get(entityType);
    }

    /**
     * Retrieves a list of eligible DungeonEntityTypes based on the current wave ID.
     * Filters out types where the minimum wave is greater than the current wave and excludes the END_CRYSTAL type.
     *
     * @param currentWaveId The current wave ID used to filter eligible entities.
     * @return A list of eligible DungeonEntityTypes for the current wave.
     */
    public static List<DungeonEntityType> getEligibleEntities(int currentWaveId) {
        return cache.values().stream()
                .filter(type -> type.getMinimunWave() <= currentWaveId)
                .filter(type -> type.getEntityType() != EntityTypes.END_CRYSTAL)
                .collect(Collectors.toList());
    }
}
