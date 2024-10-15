package com.dungeon.common.hotbar.factory;

import com.dungeon.common.hotbar.HotBarItem;
import com.dungeon.common.hotbar.impl.PauseHotBarItem;
import com.dungeon.common.model.dungeon.Dungeon;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HotBarItemFactory {

    private static final Map<Integer, HotBarItem> cache = new HashMap<>() {
        {
            put(4, new PauseHotBarItem());
        }
    };

    public static HotBarItem getHotBarItem(int slot) {
        return cache.get(slot);
    }

    public static void giveItems(Player player, Dungeon dungeon) {
        player.getInventory().clear();

        for (HotBarItem item : cache.values()) {
            item.give(player, dungeon);
        }
    }
}
