package com.dungeon.common.util;

import com.github.retrooper.packetevents.util.Vector3d;

public class DirectionUtil {

    public static float getPitch(Vector3d origin, Vector3d target) {
        double distanceXZ = calculateDistanceXZ(origin, target); // Distância no plano XZ
        return (float) Math.toDegrees(Math.atan2(target.getY() - origin.getY(), distanceXZ));
    }

    public static float getYaw(Vector3d origin, Vector3d target) {
        return (float) Math.toDegrees(Math.atan2(target.getX() - origin.getX(), target.getZ() - origin.getZ()));
    }

    private static double calculateDistanceXZ(Vector3d first, Vector3d second) {
        return Math.sqrt(Math.pow(second.getX() - first.getX(), 2) + Math.pow(second.getZ() - first.getZ(), 2));
    }

    public static Vector3d getDirection(Vector3d origin, Vector3d target) {
        float yaw = getYaw(origin, target);
        float pitch = getPitch(origin, target);

        return new Vector3d(pitch, yaw, 0);
    }

}
