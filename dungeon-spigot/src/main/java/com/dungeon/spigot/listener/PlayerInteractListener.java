package com.dungeon.spigot.listener;

import com.dungeon.common.cache.DungeonCache;
import com.dungeon.common.hotbar.HotBarItem;
import com.dungeon.common.hotbar.factory.HotBarItemFactory;
import com.dungeon.common.model.dungeon.Dungeon;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final DungeonCache dungeonCache;

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();

        final int heldItemSlot = inventory.getHeldItemSlot();
        final HotBarItem hotBarItem = HotBarItemFactory.getHotBarItem(heldItemSlot);
        if (hotBarItem == null) return;

        final Dungeon dungeon = dungeonCache.get(player.getName());
        hotBarItem.onClick(dungeon).accept(event);
    }
}
