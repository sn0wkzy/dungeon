package com.dungeon.common.model.entity;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.dungeon.wave.DungeonWave;
import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.scanner.EntityScanner;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import lombok.Getter;

import java.util.LinkedList;

@Getter
public class DungeonTotemEntityInstance extends DungeonEntityInstance {

    private final EntityScanner entityScanner;

    public DungeonTotemEntityInstance(EntityType entityType, DungeonEntityHandler dungeonEntityHandler, Vector3d currentPosition) {
        super(entityType, dungeonEntityHandler, currentPosition);
        this.entityScanner = new EntityScanner(currentPosition, getDungeonEntityStats().getAttackRadius());
    }

    @Override
    public void tick(DungeonContext dungeonContext) {
        scanAndAttack(dungeonContext);
    }

    @Override
    public void applyDamage(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance, double damage) {
        super.applyDamage(dungeonContext, dungeonEntityInstance, damage);
    }

    public void spawn(DungeonContext dungeonContext) {
        getDungeonEntityHandler().spawn(dungeonContext, this);
    }

    private void scanAndAttack(DungeonContext dungeonContext) {
        final DungeonWave dungeonWave = dungeonContext.getDungeonWave();
        entityScanner.scan(dungeonWave.getEntities().values());

        final LinkedList<Integer> toAttack = entityScanner.getToAttack();
        if (!toAttack.isEmpty()) {
            if (!canAttack()) return;

            getDungeonEntityHandler().hit(dungeonContext, this);
        }
    }
}
