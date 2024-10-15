package com.dungeon.common.model.entity.handler.impl;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.dungeon.wave.DungeonWave;
import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.entity.animation.impl.AttackLineAnimation;
import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.dungeon.common.model.scanner.EntityScanner;
import com.dungeon.common.util.NumberFormatter;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;

import java.util.LinkedList;
import java.util.Optional;

public final class TotemDungeonEntityHandler extends DungeonEntityHandler {

    public TotemDungeonEntityHandler() {
        super(EntityTypes.END_CRYSTAL);
    }

    @Override
    public void walk(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
    }

    @Override
    public void hit(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        final DungeonTotemEntityInstance dungeonTotemEntityInstance = (DungeonTotemEntityInstance) dungeonEntityInstance;
        if (dungeonTotemEntityInstance.isDead()) return;

        final EntityScanner entityScanner = dungeonTotemEntityInstance.getEntityScanner();
        final LinkedList<Integer> toAttack = new LinkedList<>(entityScanner.getToAttack());

        final DungeonWave dungeonWave = dungeonContext.getDungeonWave();

        final Integer dungeonWaveEntityId = toAttack.getFirst();
        final DungeonEntityInstance dungeonWaveEntity = dungeonWave.getEntity(dungeonWaveEntityId);

        final double currentHealth = dungeonWaveEntity.getHealth();
        final double finalDamage = dungeonTotemEntityInstance.getFinalDamage();

        final double newHealth = currentHealth - finalDamage;
        if (newHealth <= 0 || dungeonWaveEntity.isDead()) {
            dungeonContext.addKilledMob(dungeonWaveEntity.getEntityType());

            entityScanner.removeFirst();
            dungeonWave.killEntity(dungeonWaveEntity);
            return;
        }

        dungeonWaveEntity.applyDamage(dungeonContext, dungeonTotemEntityInstance, dungeonTotemEntityInstance.getFinalDamage());

        new AttackLineAnimation().play(dungeonContext, dungeonTotemEntityInstance.getCurrentPosition(), dungeonWaveEntity.getCurrentPosition(), ParticleTypes.FIREWORK, 5);
    }

    @Override
    public void updateEntityVisual(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        final int entityId = dungeonEntityInstance.getEntityId();
        final double health = dungeonEntityInstance.getHealth();
        final DungeonEntityType dungeonEntityType = dungeonEntityInstance.getDungeonEntityType();

        final WrapperPlayServerEntityMetadata wrapperPlayServerEntityMetadata = new WrapperPlayServerEntityMetadata(entityId, Lists.newArrayList(
                new EntityData(3, EntityDataTypes.BOOLEAN, true),
                new EntityData(2, EntityDataTypes.OPTIONAL_ADV_COMPONENT, Optional.of(Component.text("§4 ❤ " + NumberFormatter.format(health) + " §8| " + dungeonEntityType.getDisplayName()))))
        );
        dungeonContext.broadcastPacket(wrapperPlayServerEntityMetadata);
    }
}
