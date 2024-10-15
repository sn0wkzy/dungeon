package com.dungeon.common.cache;

import com.dungeon.common.model.dungeon.Dungeon;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class DungeonCache {

    private final Map<String, Dungeon> cache = Maps.newHashMap();

    public void register(Dungeon dungeon) {
        cache.put(dungeon.getOwner(), dungeon);
    }

    public Dungeon get(String owner) {
        return cache.get(owner);
    }

    public Collection<Dungeon> getAll() {
        return cache.values();
    }
}
