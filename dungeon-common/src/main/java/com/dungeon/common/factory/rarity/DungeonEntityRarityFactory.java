package com.dungeon.common.factory.rarity;

import com.dungeon.common.model.entity.rarity.DungeonEntityRarity;
import com.dungeon.common.model.entity.rarity.impl.EpicDungeonEntityRarity;
import com.dungeon.common.model.entity.rarity.impl.RareDungeonEntityRarity;
import com.dungeon.common.model.entity.rarity.impl.UncommonDungeonEntityRarity;

import java.util.LinkedList;

import static com.dungeon.common.keys.DungeonKeys.THREAD_LOCAL_RANDOM;

public class DungeonEntityRarityFactory {

    private static final LinkedList<DungeonEntityRarity> cache = new LinkedList<>() {
        {
            add(new UncommonDungeonEntityRarity());
            add(new RareDungeonEntityRarity());
            add(new EpicDungeonEntityRarity());
        }
    };

    /**
     * Retrieves a random rarity based on predefined spawn chances.
     * The cumulative chance is used to select the rarity, where each rarity
     * has a specific chance of being selected.
     *
     * @return A random {@link DungeonEntityRarity}, based on spawn chances.
     */
    public static DungeonEntityRarity getRandomRarity() {
        int randomValue = THREAD_LOCAL_RANDOM.nextInt(100);
        double cumulativeChance = 0.0;

        for (DungeonEntityRarity rarity : cache) {
            cumulativeChance += rarity.getSpawnChance();
            if (randomValue <= cumulativeChance) {
                return rarity;
            }
        }

        return cache.getFirst();
    }
}
