package com.dungeon.spigot.customitem;

import com.dungeon.common.cache.CustomItemCache;
import com.dungeon.common.customitem.CustomItem;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import com.dungeon.common.util.item.util.InventoryUtil;
import com.dungeon.spigot.DungeonPlugin;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@Getter
public class CustomItemAPI {

    @Getter(AccessLevel.PUBLIC)
    private static CustomItemAPI instance;

    private final CustomItemCache customItemCache;

    public CustomItemAPI(CustomItemCache customItemCache) {
        this.customItemCache = customItemCache;

        instance = this;
    }

    public @Nullable CustomItem getByKey(String key) {
        return customItemCache.getCustomItem(key);
    }

    public void give(Player player, int amount, CustomItem customItem) {
        final ItemStackBuilder itemStackBuilder = customItem.getBuilder();
        itemStackBuilder.addPersistentData(DungeonPlugin.getInstance(), "custom-item", customItem.getKey());
        itemStackBuilder.amount(amount);

        InventoryUtil.addItem(player, itemStackBuilder.build());
    }
}
