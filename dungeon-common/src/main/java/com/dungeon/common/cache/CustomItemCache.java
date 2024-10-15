package com.dungeon.common.cache;

import com.dungeon.common.customitem.CustomItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemCache {

    private final Map<String, CustomItem> cache = new HashMap<>();

    public void registerAll(CustomItem... customItems) {
        for (CustomItem customItem : customItems) {
            cache.put(customItem.getKey(), customItem);
        }
    }

    public CustomItem getCustomItem(String key) {
        return cache.get(key);
    }

    public List<CustomItem> getAll() {
        return cache.values().stream().toList();
    }
}
