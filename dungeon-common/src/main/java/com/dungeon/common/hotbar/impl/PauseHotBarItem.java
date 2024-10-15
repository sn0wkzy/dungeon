package com.dungeon.common.hotbar.impl;

import com.dungeon.common.hotbar.HotBarItem;
import com.dungeon.common.model.dungeon.Dungeon;
import com.dungeon.common.model.dungeon.wave.DungeonWave;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PauseHotBarItem extends HotBarItem {
    @Override
    public int getSlot() {
        return 4;
    }

    @Override
    public ItemStack getItem(Dungeon dungeon) {
        final DungeonWave dungeonWave = dungeon.getDungeonWave();
        if (dungeonWave.isRunning()) {
            return new ItemStackBuilder(Material.GREEN_WOOL)
                    .displayName("<green>Clique para pausar a wave</green>")
                    .build();
        }

        return new ItemStackBuilder(Material.RED_WOOL)
                .displayName("<red>Clique para continuar a wave</red>")
                .build();
    }

    @Override
    public Consumer<PlayerInteractEvent> onClick(Dungeon dungeon) {
        return event -> {
            final DungeonWave dungeonWave = dungeon.getDungeonWave();
            dungeonWave.setRunning(!dungeonWave.isRunning());

            final Player player = event.getPlayer();
            update(player, dungeon);
        };
    }
}
