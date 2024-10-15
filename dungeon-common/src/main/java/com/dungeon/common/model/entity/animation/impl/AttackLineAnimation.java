package com.dungeon.common.model.entity.animation.impl;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.animation.ParticleAnimation;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;

public class AttackLineAnimation implements ParticleAnimation {

    @Override
    public void play(DungeonContext dungeonContext, Vector3d initialPosition, Vector3d targetPosition, ParticleType<?> particleType, int particleCount) {
        final double distance = targetPosition.distance(initialPosition);
        final Vector3d direction = targetPosition.subtract(initialPosition).normalize();
        for (int i = 1; i < distance; i++) {
            final Vector3d particlePosition = initialPosition.add(direction.multiply(i));

            final WrapperPlayServerParticle wrapperPlayServerParticle = new WrapperPlayServerParticle(
                    new Particle<>(particleType),
                    true,
                    particlePosition,
                    new Vector3f(0f, 0f, 0f),
                    0f,
                    particleCount
            );
            dungeonContext.broadcastPacket(wrapperPlayServerParticle);
        }
    }
}
