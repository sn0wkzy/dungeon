package com.dungeon.spigot.listener;

import com.dungeon.common.cache.DungeonCache;
import com.dungeon.common.hotbar.factory.HotBarItemFactory;
import com.dungeon.common.model.dungeon.Dungeon;
import com.dungeon.spigot.DungeonPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PlayerConnectionListener implements Listener {

    private final DungeonCache dungeonCache;

    @EventHandler
    private void onJoin(@NotNull PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final Dungeon dungeon = new Dungeon(player.getName());
        dungeonCache.register(dungeon);

        HotBarItemFactory.giveItems(player, dungeon);
        Bukkit.getScheduler().runTaskLaterAsynchronously(DungeonPlugin.getInstance(), dungeon::start, 20L);
    }
}
