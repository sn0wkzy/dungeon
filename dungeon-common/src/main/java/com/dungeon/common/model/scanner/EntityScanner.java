package com.dungeon.common.model.scanner;

import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.github.retrooper.packetevents.util.Vector3d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * The EntityScanner class is responsible for scanning nearby entities within a specified radius
 * and identifying potential targets to attack.
 * It maintains a list of entities that can be attacked and handles periodic scans to update this list.
 */
@RequiredArgsConstructor
@Getter
public class EntityScanner {

    private final Vector3d position;
    private final double radius;

    private long lastScan = 0L;
    private final LinkedList<Integer> toAttack = new LinkedList<>();

    /**
     * Scans the provided collection of entities to identify those that are within the attackable range
     * and not already marked for attack.
     *
     * @param aliveEntities The collection of entities to be scanned.
     */
    public void scan(Collection<DungeonEntityInstance> aliveEntities) {
        if (!canScan()) return;

        aliveEntities.stream()
                .filter(dungeonEntityInstance -> !dungeonEntityInstance.isDead())
                .forEach(dungeonEntityInstance -> {
                    if (!canAttack(dungeonEntityInstance.getCurrentPosition(), radius) || hasEntity(dungeonEntityInstance.getEntityId()))
                        return;

                    toAttack.add(dungeonEntityInstance.getEntityId());
                });

        lastScan = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2);
    }

    /**
     * Removes the first entity from the list of entities to attack.
     */
    public void removeFirst() {
        toAttack.removeFirst();
    }

    /**
     * Determines whether an entity at the given position is within the attackable range.
     *
     * @param targetPosition The position of the target entity.
     * @param radius         The radius within which the target must be to be attackable.
     * @return True if the entity is within range, false otherwise.
     */
    private boolean canAttack(Vector3d targetPosition, double radius) {
        double distanceSquared = Math.pow(targetPosition.x - position.x, 2) + Math.pow(targetPosition.z - position.z, 2);
        return distanceSquared <= Math.pow(radius, 2);
    }

    /**
     * Checks if the entity with the given ID is already in the list of entities to attack.
     *
     * @param entityId The ID of the entity.
     * @return True if the entity is already in the attack list, false otherwise.
     */
    private boolean hasEntity(int entityId) {
        return toAttack.stream().anyMatch(id -> id == entityId);
    }

    /**
     * Determines whether a new scan can be performed based on the time elapsed since the last scan.
     *
     * @return True if the scanner can perform a new scan, false otherwise.
     */
    private boolean canScan() {
        return lastScan < System.currentTimeMillis();
    }
}
