package com.dungeon.common.packet.impl.display;

import com.dungeon.common.packet.prototype.impl.DisplayPacketEntity;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import org.bukkit.entity.Player;

import java.util.Set;

public final class BlockDisplayEntity extends DisplayPacketEntity {
    public BlockDisplayEntity(Set<Player> viewers, Vector3d location) {
        super(viewers, EntityTypes.BLOCK_DISPLAY, location);
    }

    public void setBlock(int blockId) {
        addMetaData(23, EntityDataTypes.BLOCK_STATE, blockId);
    }
}
