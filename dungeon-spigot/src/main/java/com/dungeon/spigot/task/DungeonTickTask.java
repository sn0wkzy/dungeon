package com.dungeon.spigot.task;

import com.dungeon.common.cache.DungeonCache;
import com.dungeon.common.model.dungeon.Dungeon;
import com.dungeon.common.model.dungeon.wave.DungeonWave;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DungeonTickTask implements Runnable {

    private final DungeonCache dungeonCache;

    @Override
    public void run() {
        for (Dungeon dungeon : dungeonCache.getAll()) {
            final DungeonWave dungeonWave = dungeon.getDungeonWave();
            if (!dungeonWave.isRunning()) continue;

            if (dungeonWave.isFinished()) {
                dungeon.setupNextWave();
                return;
            }

            dungeonWave.tick();
        }
    }
}
