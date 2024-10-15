package com.dungeon.common.model.entity.rarity.impl;

import com.dungeon.common.model.entity.rarity.DungeonEntityRarity;

public class UncommonDungeonEntityRarity extends DungeonEntityRarity {
    @Override
    public String getDisplayName() {
        return "§7Incomun";
    }

    @Override
    public double statsIncreasePercentage() {
        return 0;
    }

    @Override
    public double getSpawnChance() {
        return 80;
    }
}
