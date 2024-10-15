package com.dungeon.common.model.entity.animation;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import com.github.retrooper.packetevents.util.Vector3d;

public interface ParticleAnimation {

    void play(DungeonContext dungeonContext, Vector3d initialPosition, Vector3d targetPosition, ParticleType<?> particleType, int particleCount);
}
