package com.dungeon.common.packet.prototype.impl;

import com.dungeon.common.packet.prototype.AbstractPacketEntity;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Quaternion4f;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import org.bukkit.entity.Player;

import java.util.Set;

public class DisplayPacketEntity extends AbstractPacketEntity {

    public DisplayPacketEntity(Set<Player> viewers, EntityType entityType, Vector3d location) {
        super(viewers, entityType, location);
    }

    public void setInterpolationDelay(int interpolationDelay) {
        addMetaData(8, EntityDataTypes.INT, interpolationDelay);
    }

    public void setTransformationInterpolationDuration(int interpolationDelay) {
        addMetaData(9, EntityDataTypes.INT, interpolationDelay);
    }

    public void setPositionInterpolationDuration(int interpolationDelay) {
        addMetaData(10, EntityDataTypes.INT, interpolationDelay);
    }

    public void setTranslation(Vector3f vector3f) {
        addMetaData(11, EntityDataTypes.VECTOR3F, vector3f);
    }

    public void setScale(Vector3f vector3f) {
        addMetaData(12, EntityDataTypes.VECTOR3F, vector3f);
    }

    public void setLeftRotation(Quaternion4f quaternion4f) {
        addMetaData(13, EntityDataTypes.QUATERNION, quaternion4f);
    }

    public void setRightRotation(Quaternion4f quaternion4f) {
        addMetaData(14, EntityDataTypes.QUATERNION, quaternion4f);
    }

    public void setViewRange(Float range) {
        addMetaData(17, EntityDataTypes.FLOAT, range);
    }

    public void setShadowRadius(Float shadowRadius) {
        addMetaData(18, EntityDataTypes.FLOAT, shadowRadius);
    }

    public void setShadowStrength(Float shadowStrength) {
        addMetaData(19, EntityDataTypes.FLOAT, shadowStrength);
    }

    public void setWidth(Float width) {
        addMetaData(20, EntityDataTypes.FLOAT, width);
    }

    public void setHeight(Float height) {
        addMetaData(21, EntityDataTypes.FLOAT, height);
    }

    public void setGlowColor(int glowColor) {
        addMetaData(22, EntityDataTypes.INT, glowColor);
    }
}
