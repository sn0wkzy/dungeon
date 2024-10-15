package com.dungeon.common.customitem.impl;

import com.dungeon.common.cache.DungeonCache;
import com.dungeon.common.customitem.CustomItem;
import com.dungeon.common.event.impl.CustomItemInteractListener;
import com.dungeon.common.model.dungeon.Dungeon;
import com.dungeon.common.model.dungeon.barrier.Barrier;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import com.dungeon.common.util.item.util.InventoryUtil;
import com.github.retrooper.packetevents.util.Vector3d;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.greenrobot.eventbus.Subscribe;

public class BarrierCustomItem extends CustomItem {

    private final DungeonCache dungeonCache;

    public BarrierCustomItem(DungeonCache dungeonCache) {
        super("barrier");

        this.dungeonCache = dungeonCache;
    }

    @Override
    public ItemStackBuilder getBuilder() {
        return new ItemStackBuilder(Material.IRON_BARS)
                .displayName("<gray>Barreira</gray>")
                .addLore("<gray>Use esse item para impedir que</gray>",
                        "<gray>os mobs cheguem ao seu totem.</gray>");
    }

    @Subscribe
    public void onInteract(CustomItemInteractListener event) {
        if (event.getClickedLocation() == null) return;

        final Player player = event.getPlayer();
        final Dungeon dungeon = dungeonCache.get(player.getName());

        final Vector3d location = event.getClickedLocation();
        final Barrier barrier = new Barrier(dungeon, location);

        dungeon.addBarrier(barrier);
        InventoryUtil.removeItemFromHand(player, 1);
    }
}
