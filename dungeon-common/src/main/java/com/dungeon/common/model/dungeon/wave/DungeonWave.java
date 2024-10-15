package com.dungeon.common.model.dungeon.wave;

import com.dungeon.common.factory.entity.DungeonEntityFactory;
import com.dungeon.common.factory.type.DungeonEntityTypeFactory;
import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dungeon.common.keys.DungeonKeys.*;

/**
 * The DungeonWave class represents a wave of entities in a dungeon context.
 * Each wave is associated with a specific wave ID and consists of multiple entities
 * that are spawned and managed during the wave's execution.
 * This class handles loading entities, updating their behavior per tick, and tracking the wave's progress.
 */
@Data
public class DungeonWave {

    private final DungeonContext dungeonContext;
    private final int waveId;

    private final Map<Integer, DungeonEntityInstance> entities = new HashMap<>();
    private boolean running = false;

    public DungeonWave(DungeonContext dungeonContext, int waveId) {
        this.dungeonContext = dungeonContext;
        this.waveId = waveId;
    }

    /**
     * Loads and spawns entities for this wave.
     * The number and type of entities spawned depends on the wave ID.
     * Eligible entities are determined by the wave ID, and each entity is spawned
     * at a random location.
     */
    public void loadEntities() {
        if (!entities.isEmpty()) entities.clear();

        final List<DungeonEntityType> eligibleEntities = DungeonEntityTypeFactory.getEligibleEntities(waveId);

        final int entityCount = ENTITY_PER_WAVE * waveId;
        for (int i = 0; i <= entityCount; i++) {
            final int eligibleEntityIndex = THREAD_LOCAL_RANDOM.nextInt(0, eligibleEntities.size());
            final DungeonEntityType dungeonEntityType = eligibleEntities.get(eligibleEntityIndex);

            final int spawnLocationIndex = THREAD_LOCAL_RANDOM.nextInt(0, SPAWN_LOCATIONS.size());
            final Vector3d spawnLocation = SPAWN_LOCATIONS.get(spawnLocationIndex);

            final DungeonEntityHandler dungeonEntityHandler = DungeonEntityFactory.getEntity(dungeonEntityType.getEntityType());
            final DungeonEntityInstance dungeonEntityInstance = new DungeonEntityInstance(dungeonEntityHandler.getEntityType(), dungeonEntityHandler, spawnLocation);

            dungeonEntityHandler.spawn(dungeonContext, dungeonEntityInstance);
            entities.put(dungeonEntityInstance.getEntityId(), dungeonEntityInstance);
        }
    }

    /**
     * Updates the state of the wave by processing a tick for each entity.
     * Each entity is given an opportunity to perform its actions.
     * Additionally, the dungeon's totem is updated every tick.
     */
    public void tick() {
        for (DungeonEntityInstance dungeonEntityInstance : entities.values()) {
            dungeonEntityInstance.tick(dungeonContext);
        }

        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        dungeonTotem.tick(dungeonContext);
    }

    /**
     * Removes an entity from the wave by marking it as killed and removing it from the list.
     * Also broadcasts the destruction of the entity to all players in the dungeon.
     *
     * @param dungeonEntityInstance The entity instance to be killed.
     */
    public void killEntity(DungeonEntityInstance dungeonEntityInstance) {
        final int entityId = dungeonEntityInstance.getEntityId();
        if (!hasEntity(entityId)) return;

        removeEntity(entityId);

        final WrapperPlayServerDestroyEntities wrapperPlayServerDestroyEntities = new WrapperPlayServerDestroyEntities(entityId);
        dungeonContext.broadcastPacket(wrapperPlayServerDestroyEntities);

        dungeonContext.updateDisplay();
    }

    public int getAliveEntitiesCount() {
        return (int) entities.values().stream()
                .filter(entity -> !entity.isDead())
                .count();
    }

    public boolean hasEntity(int entityId) {
        return entities.containsKey(entityId);
    }

    public DungeonEntityInstance getEntity(int entityId) {
        return entities.get(entityId);
    }

    public void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public boolean isFinished() {
        return entities.isEmpty();
    }
}
