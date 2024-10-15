package com.dungeon.common.model.entity.rarity.impl;

import com.dungeon.common.model.entity.rarity.DungeonEntityRarity;

public class EpicDungeonEntityRarity extends DungeonEntityRarity {
    @Override
    public String getDisplayName() {
        return "§5Épico";
    }

    @Override
    public double statsIncreasePercentage() {
        return 30;
    }

    @Override
    public double getSpawnChance() {
        return 50;
    }
}
