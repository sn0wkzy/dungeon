package com.dungeon.common.packet.prototype;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import net.kyori.adventure.text.Component;

import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import static com.dungeon.common.keys.DungeonKeys.PACKET_EVENTS_API;

public abstract class AbstractPacketEntity {


    public final int entityId = SpigotReflectionUtil.generateEntityId();
    public final LinkedList<EntityData> metaDatas = new LinkedList<>();

    public final EntityType entityType;
    public final Vector3d location;

    protected AbstractPacketEntity(EntityType entityType, Vector3d location) {
        this.entityType = entityType;
        this.location = location;
    }

    public void spawn(DungeonContext dungeonContext) {
        broadcastPacket(dungeonContext, createSpawnPacket());
    }

    private WrapperPlayServerSpawnEntity createSpawnPacket() {
        return new WrapperPlayServerSpawnEntity(
                entityId,
                Optional.of(UUID.randomUUID()),
                entityType,
                location,
                0F,
                0F,
                0F,
                0,
                Optional.empty()
        );
    }

    public void despawn(DungeonContext dungeonContext) {
        broadcastPacket(dungeonContext, createDespawnPacket());
    }

    public void update(DungeonContext dungeonContext) {
        broadcastPacket(dungeonContext, createUpdatePacket());
        metaDatas.clear();
    }

    public void addMetaData(int id, EntityDataType<?> type, Object value) {
        metaDatas.add(new EntityData(id, type, value));
    }

    private WrapperPlayServerDestroyEntities createDespawnPacket() {
        return new WrapperPlayServerDestroyEntities(entityId);
    }

    private WrapperPlayServerEntityMetadata createUpdatePacket() {
        return new WrapperPlayServerEntityMetadata(entityId, metaDatas);
    }

    public void setCustomName(Component component) {
        addMetaData(2, EntityDataTypes.OPTIONAL_ADV_COMPONENT, component);
    }

    public void setCustomNameVisible() {
        addMetaData(3, EntityDataTypes.BOOLEAN, 1);
    }

    public void setInvisible() {
        addMetaData(0, EntityDataTypes.BYTE, 0x20);
    }

    protected void broadcastPacket(DungeonContext dungeonContext, PacketWrapper<?> packetWrapper) {
        dungeonContext.getViewers().forEach(player -> PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packetWrapper));

    }
}
