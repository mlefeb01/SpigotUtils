package com.github.mlefeb01.spigotutils.api.utils;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Utility methods for common {@link Player} operations
 *
 * @author Matt Lefebvre
 */
public final class PlayerUtils {

    private PlayerUtils() {
        throw new AssertionError();
    }

    /**
     * Safely gives a player an item by either adding it to their inventory or if full, dropping it on the ground
     *
     * @param player the player to give the item to
     * @param item   the item given to the player
     */
    public static void safeItemGive(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    /**
     * Attempts to apply a potion effect to a player
     * 1. If the player does not have the effect, it will be applied
     * 2. If the player already has the potion there are three scenarios
     *   2a. If the potion attempting to be applied has a higher amplifier than the current effect, it will be overridden
     *   2b. If the potion amplifiers are equal but the incoming potion has a longer duration it will be overridden
     *   2c. If the incoming potion effect does not have an amplifier or duration advantage over the current effect it will be ignored
     *
     * @param player player
     * @param potion potion effect
     */
    public static void safePotionApply(Player player, PotionEffect potion) {
        if (player.hasPotionEffect(potion.getType())) {
            // Spigot versions above 1.8 have an actual method to do this but 1.8 does not, so do it manually
            final PotionEffect active = player.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType() == potion.getType()).limit(1).collect(Collectors.toList()).get(0);

            // potion level higher than current potion level
            if (potion.getAmplifier() > active.getAmplifier()) {
                player.addPotionEffect(potion, true);

            // potion / current levels are equal but the new potion has a longer duration, so override it
            } else if (potion.getAmplifier() == active.getAmplifier() && active.getDuration() < potion.getDuration()) {
                player.addPotionEffect(potion, true);
            }

        // player does not have the potion effect at all so just apply it
        } else {
            player.addPotionEffect(potion);
        }
    }

    /**
     * Removes specific potion effects from a player
     *
     * @param player  player
     * @param potions potion effects
     */
    public static void removePotionEffects(Player player, Collection<PotionEffectType> potions) {
        potions.stream().filter(player::hasPotionEffect).forEach(player::removePotionEffect);
    }

    /**
     * Returns the block face corresponding to the players direction
     * Based off of: https://www.spigotmc.org/threads/player-direction.175482/
     *
     * @param player player
     * @return blockface
     */
    public static BlockFace getDirection(Player player) {
        int degrees = (Math.round(player.getLocation().getYaw()) + 360) % 360;
        if (degrees <= 22) {
            return BlockFace.NORTH;
        } else if (degrees <= 67) {
            return BlockFace.SOUTH_WEST;
        } else if (degrees <= 112) {
            return BlockFace.WEST;
        } else if (degrees <= 157) {
            return BlockFace.NORTH_WEST;
        } else if (degrees <= 202) {
            return BlockFace.NORTH;
        } else if (degrees <= 247) {
            return BlockFace.NORTH_EAST;
        } else if (degrees <= 292) {
            return BlockFace.EAST;
        } else if (degrees <= 337) {
            return BlockFace.SOUTH_EAST;
        } else if (degrees <= 359) {
            return BlockFace.SOUTH;
        } else {
            return null;
        }
    }

    /**
     * Returns the blockface corresponding to the players opposite direction
     * Based off of: https://www.spigotmc.org/threads/player-direction.175482/
     *
     * @param player
     * @return
     */
    public static BlockFace getOppositeDirection(Player player) {
        int degrees = (Math.round(player.getLocation().getYaw()) + 360) % 360;
        if (degrees <= 22) {
            return BlockFace.SOUTH;
        } else if (degrees <= 67) {
            return BlockFace.NORTH_EAST;
        } else if (degrees <= 112) {
            return BlockFace.EAST;
        } else if (degrees <= 157) {
            return BlockFace.SOUTH_EAST;
        } else if (degrees <= 202) {
            return BlockFace.SOUTH;
        } else if (degrees <= 247) {
            return BlockFace.SOUTH_WEST;
        } else if (degrees <= 292) {
            return BlockFace.WEST;
        } else if (degrees <= 337) {
            return BlockFace.NORTH_WEST;
        } else if (degrees <= 359) {
            return BlockFace.NORTH;
        } else {
            return null;
        }
    }

    /**
     * Check if a player has an empty inventory
     *
     * @param player player
     * @return has empty inventory
     */
    public static boolean hasEmptyInventory(Player player) {
        final PlayerInventory inventory = player.getInventory();
        for (ItemStack armor : inventory.getArmorContents()) {
            if (!ItemUtils.isNullOrAir(armor)) {
                return false;
            }
        }
        for (ItemStack content : inventory.getContents()) {
            if (!ItemUtils.isNullOrAir(content)) {
                return false;
            }
        }
        return true;
    }

}
