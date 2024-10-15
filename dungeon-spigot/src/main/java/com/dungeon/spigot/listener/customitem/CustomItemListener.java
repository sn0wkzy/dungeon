package com.dungeon.spigot.listener.customitem;

import com.dungeon.common.cache.CustomItemCache;
import com.dungeon.common.customitem.CustomItem;
import com.dungeon.common.event.impl.CustomItemInteractListener;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import com.dungeon.spigot.DungeonPlugin;
import com.github.retrooper.packetevents.util.Vector3d;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.greenrobot.eventbus.EventBus;

@RequiredArgsConstructor
public class CustomItemListener implements Listener {

    private final CustomItemCache customItemCache;

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) return;

        final ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;

        final ItemStackBuilder itemStackBuilder = new ItemStackBuilder(item);
        if (!itemStackBuilder.hasPersistentData(DungeonPlugin.getInstance(), "custom-item")) return;

        final String customItemKey = itemStackBuilder.getPersistentData(DungeonPlugin.getInstance(), "custom-item");
        final CustomItem customItem = customItemCache.getCustomItem(customItemKey);
        if (customItem == null) return;

        event.setCancelled(true);

        Vector3d location = null;
        if (event.getClickedBlock() != null) {
            final Block clickedBlock = event.getClickedBlock();
            final Location clickedBlockLocation = clickedBlock.getLocation();

            location = new Vector3d(clickedBlockLocation.getX(), clickedBlockLocation.getY() + 1, clickedBlockLocation.getZ());
        }

        final Player player = event.getPlayer();

        final EventBus eventBus = customItem.getEventBus();
        eventBus.post(new CustomItemInteractListener(player, customItem, location));
    }
}
