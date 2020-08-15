package com.github.mlefeb01.spigotutils.api.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility methods for common {@link Location} operations
 *
 * @author Matt Lefebvre
 */
public final class LocationUtils {

    private LocationUtils() {
        throw new AssertionError();
    }

    /**
     * Creates a minimum location given two locations (useful for area checks)
     *
     * @param location  location 1
     * @param location2 location 2
     * @return minimum location
     */
    public static Location getMinimumLocation(Location location, Location location2) {
        return new Location(location.getWorld(), Math.min(location.getX(), location2.getX()), Math.min(location.getY(), location2.getY()), Math.min(location.getZ(), location2.getZ()));
    }

    /**
     * Creates a maxmimum location given two locations (useful for area checks)
     *
     * @param location  location 1
     * @param location2 location 2
     * @return maximum location
     */
    public static Location getMaximumLocation(Location location, Location location2) {
        return new Location(location.getWorld(), Math.max(location.getX(), location2.getX()), Math.max(location.getY(), location2.getY()), Math.max(location.getZ(), location2.getZ()));
    }

    /**
     * Gets all blocks within a radius of a given location
     *
     * @param location location
     * @param radius   radius
     * @return all blocks within x radius of the provided location
     */
    public static List<Block> getNearbyBlocks(Location location, int radius) {
        return getNearbyBlocks(location, radius, null);
    }

    /**
     * Gets all blocks within a radius of a given location with a given type
     *
     * @param location location
     * @param radius radius
     * @param material material
     * @return all blocks within x radius of the provided location with of the provided type
     */
    public static List<Block> getNearbyBlocks(Location location, int radius, Material material) {
        final List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    final Block block = location.getWorld().getBlockAt(x, y, z);
                    if (material != null && block.getType() != material) {
                        continue;
                    }
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }


    /**
     * Gets all entities within a radius of a location
     *
     * @param location location
     * @param radius radius
     * @return nearby entities
     */
    public static List<Entity> getNearbyEntities(Location location, double radius) {
        return new ArrayList<>(location.getWorld().getNearbyEntities(location, radius, radius, radius));
    }

    /**
     * Gets entities within a radius of a location with the specified entity type
     *
     * @param location location
     * @param radius radius
     * @param type entity type
     * @return nearby entities with the given type
     */
    public static List<Entity> getNearbyEntities(Location location, double radius, EntityType type) {
        return location.getWorld().getNearbyEntities(location, radius, radius, radius).stream().filter(entity -> entity.getType() == type).collect(Collectors.toList());
    }

}
