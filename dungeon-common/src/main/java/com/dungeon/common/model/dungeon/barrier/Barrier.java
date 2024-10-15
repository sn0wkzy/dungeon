package com.dungeon.common.model.dungeon.barrier;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.dungeon.common.packet.impl.display.BlockDisplayEntity;
import com.dungeon.common.packet.impl.display.TextDisplayEntity;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.concurrent.TimeUnit;

@Getter
public class Barrier {

    private final DungeonContext dungeonContext;
    private final Vector3d location;

    private final TextDisplayEntity textDisplayEntity;
    private final BlockDisplayEntity blockDisplayEntity;

    private double health = 10;

    public Barrier(DungeonContext dungeonContext, Vector3d location) {
        this.dungeonContext = dungeonContext;
        this.location = location;

        textDisplayEntity = new TextDisplayEntity(dungeonContext.getPlayers(), location.add(0.5, 1.0, 0.5));
        initializeDisplayText();

        this.blockDisplayEntity = new BlockDisplayEntity(dungeonContext.getPlayers(), location);
        initializeDisplayBlock();
    }

    public void applyDamage(double damage, DungeonEntityInstance dungeonEntityInstance) {
        final DungeonEntityStats dungeonEntityStats = dungeonEntityInstance.getDungeonEntityStats();
        dungeonEntityInstance.setHitDelay(System.currentTimeMillis() + TimeUnit.MILLISECONDS.toMillis(dungeonEntityStats.getHitDelay()));

        double newHealth = health - damage;
        if (newHealth > 0) {
            this.health = newHealth;

            updateDisplayText();
            return;
        }

        dungeonContext.removeBarrier(this);

        textDisplayEntity.despawn();
        blockDisplayEntity.despawn();
    }

    public void initializeDisplayText() {
        textDisplayEntity.spawn(new Vector3f(1, 1, 1), 100F, 100F);
        updateDisplayText();
    }

    public void updateDisplayText() {
        textDisplayEntity.setTitle(MiniMessage.miniMessage().deserialize("<color:#ff3300>❤ " + health + "</color>"));
        textDisplayEntity.update();
    }

    public void initializeDisplayBlock() {
        blockDisplayEntity.spawn();

        blockDisplayEntity.setBlock(StateTypes.IRON_BARS.createBlockState().getGlobalId());
        blockDisplayEntity.update();
    }
}
