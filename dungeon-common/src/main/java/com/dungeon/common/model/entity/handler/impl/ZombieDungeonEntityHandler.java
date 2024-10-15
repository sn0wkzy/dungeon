package com.dungeon.common.model.entity.handler.impl;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.dungeon.barrier.Barrier;
import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;

import java.util.LinkedList;

public final class ZombieDungeonEntityHandler extends DungeonEntityHandler {

    public ZombieDungeonEntityHandler() {
        super(EntityTypes.ZOMBIE);
    }

    @Override
    public void walk(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        final LinkedList<Vector3d> positions = dungeonEntityInstance.getPositions();
        final Vector3d nextPosition = positions.getFirst();
        if (dungeonContext.hasBarrier(nextPosition)) {
            hit(dungeonContext, dungeonEntityInstance);
            return;
        }

        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        final Vector3d dungeonTotemLocation = dungeonTotem.getCurrentPosition();

        final Vector3d currentPosition = dungeonEntityInstance.getCurrentPosition();
        final double distance = currentPosition.distance(dungeonTotemLocation);

        final DungeonEntityStats dungeonEntityStats = dungeonEntityInstance.getDungeonEntityStats();
        if (distance <= dungeonEntityStats.getAttackRadius()) {
            hit(dungeonContext, dungeonEntityInstance);
            return;
        }

        dungeonEntityInstance.setCurrentPosition(nextPosition);
        walkTo(dungeonContext, dungeonEntityInstance, currentPosition);

        positions.removeFirst();
    }

    @Override
    public void hit(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        if (!dungeonEntityInstance.canAttack()) return;

        final LinkedList<Vector3d> positions = dungeonEntityInstance.getPositions();
        final Vector3d nextPosition = positions.getFirst();
        if (dungeonContext.hasBarrier(nextPosition)) {
            final Barrier barrier = dungeonContext.getBarrier(nextPosition);

            barrier.applyDamage(2.5, dungeonEntityInstance);
            hitAnimation(dungeonContext, dungeonEntityInstance);
            return;
        }

        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        if (dungeonTotem.isDead()) return;

        dungeonTotem.applyDamage(dungeonContext, dungeonEntityInstance, dungeonEntityInstance.getFinalDamage());
        hitAnimation(dungeonContext, dungeonEntityInstance);
    }
}
