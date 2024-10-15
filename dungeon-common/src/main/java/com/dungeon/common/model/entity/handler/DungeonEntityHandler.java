package com.dungeon.common.model.entity.handler;

import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.DungeonEntityInstance;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.entity.rarity.DungeonEntityRarity;
import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.dungeon.common.util.DirectionUtil;
import com.dungeon.common.util.NumberFormatter;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityAnimation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.google.common.collect.Lists;
import lombok.Data;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Abstract class responsible for handling the behavior of dungeon entities.
 * This class defines the core actions and interactions entities perform within a dungeon,
 * such as walking, hitting, spawning, and updating their visual state.
 */
@Data
public abstract class DungeonEntityHandler {

    public final EntityType entityType;

    public DungeonEntityHandler(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * Handles the entity's walking behavior. By default, this method is empty and can be overridden by subclasses.
     *
     * @param dungeonContext        The current context of the dungeon.
     * @param dungeonEntityInstance The instance of the entity performing the walk action.
     */
    public void walk(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
    }

    /**
     * Handles the entity's hitting behavior. By default, this method is empty and can be overridden by subclasses.
     *
     * @param dungeonContext        The current context of the dungeon.
     * @param dungeonEntityInstance The instance of the entity performing the hit action.
     */
    public void hit(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
    }

    /**
     * Spawns the entity in the dungeon and broadcasts its appearance to all players.
     * Also initializes the entity's movement path and visual state.
     *
     * @param dungeonContext        The current context of the dungeon.
     * @param dungeonEntityInstance The instance of the entity being spawned.
     */
    public void spawn(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        dungeonEntityInstance.calculatePositions(dungeonContext);

        final int entityId = dungeonEntityInstance.getEntityId();
        final Vector3d currentPosition = dungeonEntityInstance.getCurrentPosition();

        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        final Vector3d dungeonTotemCurrentPosition = dungeonTotem.getCurrentPosition();

        final WrapperPlayServerSpawnEntity wrapperPlayServerSpawnEntity = new WrapperPlayServerSpawnEntity(
                entityId,
                Optional.of(UUID.randomUUID()),
                entityType,
                currentPosition,
                DirectionUtil.getPitch(currentPosition, dungeonTotemCurrentPosition),
                DirectionUtil.getYaw(currentPosition, dungeonTotemCurrentPosition),
                DirectionUtil.getYaw(currentPosition, dungeonTotemCurrentPosition),
                0,
                Optional.empty()
        );
        dungeonContext.broadcastPacket(wrapperPlayServerSpawnEntity);

        updateEntityVisual(dungeonContext, dungeonEntityInstance);
    }

    /**
     * Updates the visual representation of the entity for all players in the dungeon.
     * This includes updating the health display and any other metadata related to the entity.
     *
     * @param dungeonContext        The current context of the dungeon.
     * @param dungeonEntityInstance The instance of the entity whose visuals are being updated.
     */
    public void updateEntityVisual(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        final int entityId = dungeonEntityInstance.getEntityId();
        final double health = dungeonEntityInstance.getHealth();
        final DungeonEntityType dungeonEntityType = dungeonEntityInstance.getDungeonEntityType();
        final DungeonEntityRarity dungeonEntityRarity = dungeonEntityInstance.getDungeonEntityRarity();

        final WrapperPlayServerEntityMetadata wrapperPlayServerEntityMetadata = new WrapperPlayServerEntityMetadata(entityId, Lists.newArrayList(
                new EntityData(3, EntityDataTypes.BOOLEAN, true),
                new EntityData(2, EntityDataTypes.OPTIONAL_ADV_COMPONENT, Optional.of(Component.text("§4 ❤ " + NumberFormatter.format(health) + " §8| " + dungeonEntityType.getDisplayName() + " §8(" + dungeonEntityRarity.getDisplayName() + "§8)"))))
        );
        dungeonContext.broadcastPacket(wrapperPlayServerEntityMetadata);
    }

    /**
     * Moves the entity towards a target position with a smooth velocity calculation,
     * updating its position within the dungeon.
     *
     * @param dungeonContext        The current context of the dungeon.
     * @param dungeonEntityInstance The instance of the entity that is moving.
     * @param targetPosition        The position the entity is moving towards.
     */
    public void walkTo(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance, Vector3d targetPosition) {
        final DungeonTotemEntityInstance dungeonTotem = dungeonContext.getDungeonTotem();
        final Vector3d dungeonTotemCurrentPosition = dungeonTotem.getCurrentPosition();

        final WrapperPlayServerEntityTeleport wrapperPlayServerEntityTeleport = new WrapperPlayServerEntityTeleport(
                dungeonEntityInstance.getEntityId(),
                targetPosition,
                DirectionUtil.getYaw(targetPosition, dungeonTotemCurrentPosition),
                DirectionUtil.getPitch(targetPosition, dungeonTotemCurrentPosition),
                true);
        dungeonContext.broadcastPacket(wrapperPlayServerEntityTeleport);
    }

    /**
     * Plays a hit animation when the entity performs an attack, visible to all players in the dungeon.
     *
     * @param dungeonContext        The current context of the dungeon.
     * @param dungeonEntityInstance The instance of the entity performing the attack.
     */
    public void hitAnimation(DungeonContext dungeonContext, DungeonEntityInstance dungeonEntityInstance) {
        final int entityId = dungeonEntityInstance.getEntityId();
        final WrapperPlayServerEntityAnimation wrapperPlayServerEntityAnimation = new WrapperPlayServerEntityAnimation(entityId, WrapperPlayServerEntityAnimation.EntityAnimationType.SWING_MAIN_ARM);
        dungeonContext.broadcastPacket(wrapperPlayServerEntityAnimation);
    }
}
