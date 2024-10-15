package com.dungeon.spigot.listener.protocol;

import com.dungeon.common.cache.DungeonCache;
import com.dungeon.common.model.dungeon.Dungeon;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.spigot.DungeonPlugin;
import com.dungeon.spigot.inventory.DungeonTotemInventory;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

@RequiredArgsConstructor
public class DungeonTotemInteractListener implements PacketListener {

    private final DungeonCache dungeonCache;
    private final ViewFrame viewFrame;

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        final PacketTypeCommon packetType = event.getPacketType();
        if (packetType != PacketType.Play.Client.INTERACT_ENTITY) return;

        final WrapperPlayClientInteractEntity wrapperPlayClientInteractEntity = new WrapperPlayClientInteractEntity(event);
        final WrapperPlayClientInteractEntity.InteractAction action = wrapperPlayClientInteractEntity.getAction();
        if (action != WrapperPlayClientInteractEntity.InteractAction.INTERACT) return;

        final User user = event.getUser();
        final Player player = Bukkit.getPlayerExact(user.getName());
        if (player == null) return;

        final Dungeon dungeon = dungeonCache.get(player.getName());

        final DungeonTotemEntityInstance dungeonTotem = dungeon.getDungeonTotem();
        final int entityId = wrapperPlayClientInteractEntity.getEntityId();
        if (dungeonTotem.getEntityId() != entityId) return;

        Bukkit.getScheduler().runTask(DungeonPlugin.getInstance(), () -> viewFrame.open(DungeonTotemInventory.class, player, Map.of("dungeonContext", dungeon)));
    }
}
