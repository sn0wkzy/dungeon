package com.dungeon.common.event.impl;

import com.dungeon.common.customitem.CustomItem;
import com.dungeon.common.event.CancellableEventWrapper;
import com.github.retrooper.packetevents.util.Vector3d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
@Getter
public class CustomItemInteractListener extends CancellableEventWrapper {

    private final Player player;
    private final CustomItem customItem;

    @Nullable
    private final Vector3d clickedLocation;

}
