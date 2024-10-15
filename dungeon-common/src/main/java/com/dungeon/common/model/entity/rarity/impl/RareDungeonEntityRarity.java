package com.dungeon.common.model.entity.rarity.impl;

import com.dungeon.common.model.entity.rarity.DungeonEntityRarity;

public class RareDungeonEntityRarity extends DungeonEntityRarity {
    @Override
    public String getDisplayName() {
        return "§bRaro";
    }

    @Override
    public double statsIncreasePercentage() {
        return 15;
    }

    @Override
    public double getSpawnChance() {
        return 70;
    }
}
