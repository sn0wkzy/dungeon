package com.dungeon.common.hotbar;

import com.dungeon.common.model.dungeon.Dungeon;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

public abstract class HotBarItem {

    public abstract int getSlot();

    public abstract ItemStack getItem(Dungeon dungeon);

    public abstract Consumer<PlayerInteractEvent> onClick(Dungeon dungeon);

    public void give(Player player, Dungeon dungeon) {
        final PlayerInventory inventory = player.getInventory();
        inventory.setItem(getSlot(), getItem(dungeon));
    }

    public void update(Player player, Dungeon dungeon) {
        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemStack = inventory.getItem(getSlot());
        if (itemStack == null) return;

        inventory.setItem(getSlot(), getItem(dungeon));
    }
}
