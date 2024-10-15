package com.dungeon.common.util;

import lombok.Builder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
public final class CustomLocation {

    private final String worldName;
    private final double x, y, z;
    private float yaw, pitch;

    public static CustomLocation of(@NotNull Location location) {
        return of(location, false);
    }

    public static CustomLocation of(@NotNull Block block) {
        final Location location = block.getLocation();
        return of(location, false);
    }

    public static CustomLocation of(@NotNull Player player) {
        final Location location = player.getLocation();
        return of(location, false);
    }

    public static CustomLocation of(@NotNull Location location, boolean complex) {
        final CustomLocation customLocation = CustomLocation.builder()
                .worldName(location.getWorld().getName())
                .x(location.getX())
                .y(location.getY())
                .z(location.getZ())
                .build();

        if (complex) {
            customLocation.setYaw(location.getYaw());
            customLocation.setPitch(location.getPitch());
        }

        return customLocation;
    }

    public static CustomLocation of(@NotNull String worldName, double x, double y, double z) {
        return CustomLocation.builder().worldName(worldName).x(x).y(y).z(z).build();
    }

    public @Nullable Location getLocation() {
        final World world = Bukkit.getWorld(worldName);
        if (world == null) return null;

        return new Location(world, x, y, z);
    }

    public @Nullable Location getComplexLocation() {
        final World world = Bukkit.getWorld(worldName);
        if (world == null) return null;

        return new Location(world, x, y, z, yaw, pitch);
    }
}
