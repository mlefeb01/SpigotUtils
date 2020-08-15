package com.github.mlefeb01.spigotutils.api.constants;

import org.bukkit.enchantments.Enchantment;

import static org.bukkit.enchantments.Enchantment.*;

/**
 * Enchant constants
 *
 * @author Matt Lefebvre
 */
public final class EnchantConstants {

    private EnchantConstants() {
        throw new AssertionError();
    }

    public static boolean isHelmetEnchant(Enchantment e) {
        return e.equals(OXYGEN) || e.equals(WATER_WORKER) || isArmorEnchant(e);
    }

    public static boolean isBootsEnchant(Enchantment e) {
        return e.equals(DEPTH_STRIDER) || e.equals(PROTECTION_FALL) || isArmorEnchant(e);
    }

    public static boolean isArmorEnchant(Enchantment e) {
        return e.equals(PROTECTION_ENVIRONMENTAL) || e.equals(PROTECTION_FIRE) || e.equals(PROTECTION_PROJECTILE)
                || e.equals(PROTECTION_EXPLOSIONS) || e.equals(THORNS) || isAnyEnchant(e);
    }

    public static boolean isSwordEnchant(Enchantment e) {
        return e.equals(DAMAGE_ALL) || e.equals(KNOCKBACK) || e.equals(LOOT_BONUS_MOBS) || e.equals(DAMAGE_UNDEAD) ||
                e.equals(DAMAGE_ARTHROPODS) || e.equals(FIRE_ASPECT) || isAnyEnchant(e);
    }

    public static boolean isShovelEnchant(Enchantment e) {
        return e.equals(DIG_SPEED) || e.equals(SILK_TOUCH) || e.equals(LOOT_BONUS_BLOCKS) || isAnyEnchant(e);
    }

    public static boolean isPickaxeEnchant(Enchantment e) {
        return isShovelEnchant(e);
    }

    public static boolean isAxeEnchant(Enchantment e) {
        return isSwordEnchant(e) || isShovelEnchant(e);
    }

    public static boolean isBowEnchant(Enchantment e) {
        return e.equals(ARROW_DAMAGE) || e.equals(ARROW_FIRE) || e.equals(ARROW_INFINITE) || e.equals(ARROW_KNOCKBACK) || isAnyEnchant(e);
    }

    public static boolean isFishingRodEnchant(Enchantment e) {
        return e.equals(LUCK) || e.equals(LURE) || isAnyEnchant(e);
    }

    public static boolean isAnyEnchant(Enchantment e) {
        return e.equals(DURABILITY);
    }

}
