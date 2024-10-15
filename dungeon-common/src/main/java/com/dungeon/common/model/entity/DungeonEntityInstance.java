package com.dungeon.common.model.entity;

import com.dungeon.common.factory.rarity.DungeonEntityRarityFactory;
import com.dungeon.common.factory.stats.DungeonEntityStatsFactory;
import com.dungeon.common.factory.type.DungeonEntityTypeFactory;
import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.handler.DungeonEntityHandler;
import com.dungeon.common.model.entity.rarity.DungeonEntityRarity;
import com.dungeon.common.model.entity.stats.DungeonEntityStats;
import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import lombok.Data;
import org.patheloper.api.pathing.result.PathState;
import org.patheloper.api.wrapper.PathEnvironment;
import org.patheloper.api.wrapper.PathPosition;
import org.patheloper.mapping.PatheticMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Represents an instance of a dungeon entity, managing its attributes, behavior,
 * and interactions in the dungeon environment.
 */
@Data
public class DungeonEntityInstance {

    private final int entityId;
    private final EntityType entityType;
    private final DungeonEntityHandler dungeonEntityHandler;

    private final DungeonEntityRarity dungeonEntityRarity;
    private final DungeonEntityType dungeonEntityType;
    private final DungeonEntityStats dungeonEntityStats;

    private double health;
    private double maxHealth;
    private double finalDamage;

    private Vector3d currentPosition;

    private long hitDelay = 0L;
    private final LinkedList<Vector3d> positions = new LinkedList<>();

    public DungeonEntityInstance(EntityType entityType, DungeonEntityHandler dungeonEntityHandler, Vector3d currentPosition) {
        this.entityId = SpigotReflectionUtil.generateEntityId();
        this.entityType = entityType;
        this.dungeonEntityHandler = dungeonEntityHandler;

        this.dungeonEntityRarity = DungeonEntityRarityFactory.getRandomRarity();
        this.dungeonEntityType = DungeonEntityTypeFactory.getType(entityType);
        this.dungeonEntityStats = DungeonEntityStatsFactory.getStats(entityType);

        this.health = getDungeonEntityStats().getMaxHealth() + ((getDungeonEntityStats().getMaxHealth() / 100) * dungeonEntityRarity.statsIncreasePercentage());
        this.maxHealth = health;

        this.finalDamage = getDungeonEntityStats().getDamage() + ((getDungeonEntityStats().getDamage() / 100) * dungeonEntityRarity.statsIncreasePercentage());
        this.currentPosition = currentPosition;
    }

    /**
     * Calculates the positions from the entity to the dungeon's totem using a pathfinding algorithm
     * and stores the result in the positions list.
     *
     * @param dungeonContext The context of the current dungeon, providing information like the dungeon's totem.
     */
    public void calculatePositions(DungeonContext dungeonContext) {
        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        final Vector3d dungeonTotemLocation = dungeonTotem.getCurrentPosition();
        PatheticMapper.newPathfinder().findPath(
                        new PathPosition(new PathEnvironment(UUID.randomUUID(), "", 0, 256), currentPosition.x, currentPosition.y, currentPosition.z),
                        new PathPosition(new PathEnvironment(UUID.randomUUID(), "", 0, 256), dungeonTotemLocation.x, dungeonTotemLocation.y - 1, dungeonTotemLocation.z), List.of(), List.of())
                .thenAccept(
                        pathfinderResult -> {
                            if (pathfinderResult.getPathState() != PathState.FOUND) return;

                            pathfinderResult
                                    .getPath()
                                    .forEach(location -> positions.add(new Vector3d(location.getX() + 0.5, location.getY(), location.getZ() + 0.5)));
                        });
    }

    /**
     * Called on each tick to handle the entity's movement in the dungeon.
     *
     * @param dungeonContext The context of the current dungeon, used for managing entity behaviors.
     */
    public void tick(DungeonContext dungeonContext) {
        getDungeonEntityHandler().walk(dungeonContext, this);
    }

    /**
     * Applies damage to this entity and updates its health accordingly.
     * The visual representation of the entity is also updated after receiving damage.
     *
     * @param dungeonContext        The context of the current dungeon.
     * @param dungeonEntityInstance The instance of the attacking entity.
     * @param damage                The amount of damage to be applied.
     */
    public void applyDamage(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance, double damage) {
        dungeonEntityInstance.setHitDelay(System.currentTimeMillis() + TimeUnit.MILLISECONDS.toMillis(dungeonEntityStats.getHitDelay()));

        this.health = Math.max(0, this.health - damage);
        getDungeonEntityHandler().updateEntityVisual(dungeonContext, this);
    }

    /**
     * Determines if the entity can attack, based on whether the hit delay has passed.
     *
     * @return true if the entity can attack, false otherwise.
     */
    public boolean canAttack() {
        return hitDelay < System.currentTimeMillis();
    }

    /**
     * Checks if the entity is dead, based on its health.
     *
     * @return true if the entity's health is 0 or less, false otherwise.
     */
    public boolean isDead() {
        return this.health <= 0;
    }
}
