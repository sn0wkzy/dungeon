package com.dungeon.common.model.dungeon;

import com.dungeon.common.model.dungeon.barrier.Barrier;
import com.dungeon.common.model.dungeon.wave.DungeonWave;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.view.KilledMob;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface DungeonContext {

    boolean hasBarrier(Vector3d position);

    Barrier getBarrier(Vector3d position);

    void addBarrier(Barrier barrier);

    void removeBarrier(Barrier barrier);

    List<KilledMob> getKilledMobs();

    void addKilledMob(EntityType entityType);

    DungeonTotemEntityInstance getDungeonTotem();

    DungeonWave getDungeonWave();

    void broadcastPacket(PacketWrapper<?> packetWrapper);

    Set<Player> getViewers();

    void updateDisplay();
}
