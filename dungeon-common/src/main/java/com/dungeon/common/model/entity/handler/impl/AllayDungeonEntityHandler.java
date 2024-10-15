package com.dungeon.common.model.entity.handler.impl;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.entity.animation.impl.AttackLineAnimation;
import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.util.Vector3d;

import java.util.LinkedList;

public final class AllayDungeonEntityHandler extends DungeonEntityHandler {

    public AllayDungeonEntityHandler() {
        super(EntityTypes.ALLAY);
    }

    @Override
    public void walk(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        final Vector3d dungeonTotemLocation = dungeonTotem.getCurrentPosition();

        final Vector3d currentPosition = dungeonEntityInstance.getCurrentPosition();
        final double distance = currentPosition.distance(dungeonTotemLocation);

        final DungeonEntityStats dungeonEntityStats = dungeonEntityInstance.getDungeonEntityStats();
        if (distance <= dungeonEntityStats.getAttackRadius()) {
            hit(dungeonContext, dungeonEntityInstance);
            return;
        }

        final LinkedList<Vector3d> positions = dungeonEntityInstance.getPositions();
        dungeonEntityInstance.setCurrentPosition(positions.getFirst());

        walkTo(dungeonContext, dungeonEntityInstance, currentPosition);

        positions.removeFirst();
    }

    @Override
    public void hit(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        if (!dungeonEntityInstance.canAttack()) return;

        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        if (dungeonTotem.isDead()) return;

        dungeonTotem.applyDamage(dungeonContext, dungeonEntityInstance, dungeonEntityInstance.getFinalDamage());

        final Vector3d currentPosition = dungeonEntityInstance.getCurrentPosition();
        final Vector3d dungeonTotemLocation = dungeonTotem.getCurrentPosition();
        new AttackLineAnimation().play(dungeonContext, currentPosition, dungeonTotemLocation, ParticleTypes.FLAME, 2);
    }
}
