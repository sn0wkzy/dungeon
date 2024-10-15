package com.dungeon.common.model.view;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class KilledMob {

    private final EntityType entityType;
    private final int amount;
}
