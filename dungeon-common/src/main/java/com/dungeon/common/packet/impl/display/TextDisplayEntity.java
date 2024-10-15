package com.dungeon.common.packet.impl.display;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.packet.prototype.impl.DisplayPacketEntity;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Set;

public final class TextDisplayEntity extends DisplayPacketEntity {

    public TextDisplayEntity(Vector3d location) {
        super(EntityTypes.TEXT_DISPLAY, location);
    }

    public void spawn(DungeonContext dungeonContext, Vector3f scale, Float height, Float width) {
        super.spawn(dungeonContext);

        setScale(scale);
        setHeight(height);
        setWidth(width);

        update(dungeonContext);
    }

    public void setTitle(Component component) {
        addMetaData(23, EntityDataTypes.ADV_COMPONENT, component);
    }

    public void setLineWidth(int size) {
        addMetaData(24, EntityDataTypes.INT, size);
    }

    public void setBackgroundColor(int color) {
        addMetaData(25, EntityDataTypes.INT, color);
    }

    public void setTextOpacity(Byte opacity) {
        addMetaData(25, EntityDataTypes.BYTE, opacity);
    }

    public void setShadow(Boolean value) {
        addMetaData(26, EntityDataTypes.BOOLEAN, value);
    }

    public void setSeeThrough() {
        addMetaData(27, EntityDataTypes.BYTE, 0x02);
    }

    public void setDefaultBackground() {
        addMetaData(27, EntityDataTypes.BYTE, 0x04);
    }
}
