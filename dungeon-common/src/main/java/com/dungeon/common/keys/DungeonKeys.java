package com.dungeon.common.keys;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.util.Vector3d;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonKeys {

    public static final PacketEventsAPI<?> PACKET_EVENTS_API = PacketEvents.getAPI();
    public static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    public static final Vector3d TOTEM_DEFAULT_LOCATION = new Vector3d(-33.5, 141.0, -35.5);

    public static final int ENTITY_PER_WAVE = 8;
    public static final List<Vector3d> SPAWN_LOCATIONS = Lists.newArrayList(
            new Vector3d(-33.5, 139.0, -64.5),
            new Vector3d(-33.5, 139.0, -5),
            new Vector3d(-38.5, 139.0, -5),
            new Vector3d(-63.5, 139.0, -35.5),
            new Vector3d(-63.5, 139.0, -39.5),
            new Vector3d(-3.5, 139.0, -36)
    );
}
