package com.dungeon.common.model.entity.rarity;

public abstract class DungeonEntityRarity {

    public abstract String getDisplayName();

    public abstract double statsIncreasePercentage();

    public abstract double getSpawnChance();
}
