package com.github.mlefeb01.spigotutils.api.constants;

import org.bukkit.Material;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Material constants
 *
 * @author Matt Lefebvre
 */
public final class MaterialConstants {

    private MaterialConstants() {
        throw new AssertionError();
    }

    public static final Set<Material> ORES = Collections.unmodifiableSet(EnumSet.of(
            Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE,
            Material.GLOWING_REDSTONE_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.QUARTZ_ORE
    ));

    public static final Set<Material> HELMETS = Collections.unmodifiableSet(EnumSet.of(
            Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET
    ));

    public static final Set<Material> CHESTPLATES = Collections.unmodifiableSet(EnumSet.of(
            Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE
    ));

    public static final Set<Material> LEGGINGS = Collections.unmodifiableSet(EnumSet.of(
            Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS
    ));

    public static final Set<Material> BOOTS = Collections.unmodifiableSet(EnumSet.of(
            Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS
    ));

    public static final Set<Material> PICKAXES = Collections.unmodifiableSet(EnumSet.of(
            Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE
    ));

    public static final Set<Material> SHOVELS = Collections.unmodifiableSet(EnumSet.of(
            Material.WOOD_SPADE, Material.STONE_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE
    ));

    public static final Set<Material> SWORDS = Collections.unmodifiableSet(EnumSet.of(
            Material.WOOD_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD
    ));

    public static final Set<Material> AXES = Collections.unmodifiableSet(EnumSet.of(
            Material.WOOD_AXE, Material.STONE_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE
    ));

    public static final Set<Material> REPAIRABLE = Collections.unmodifiableSet(EnumSet.of(
            Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.GOLD_HELMET, Material.DIAMOND_HELMET,
            Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
            Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLD_LEGGINGS, Material.DIAMOND_LEGGINGS,
            Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.GOLD_BOOTS, Material.DIAMOND_BOOTS,
            Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE,
            Material.WOOD_SPADE, Material.STONE_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE,
            Material.WOOD_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD,
            Material.WOOD_AXE, Material.STONE_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE,
            Material.BOW, Material.FISHING_ROD
    ));

    public static boolean isOreBlock(Material m) {
        return ORES.contains(m);
    }

    public static boolean isHelmet(Material m) {
        return HELMETS.contains(m);
    }

    public static boolean isChestplate(Material m) {
        return CHESTPLATES.contains(m);
    }

    public static boolean isLeggings(Material m) {
        return LEGGINGS.contains(m);
    }

    public static boolean isBoots(Material m) {
        return BOOTS.contains(m);
    }

    public static boolean isPickaxe(Material m) {
        return PICKAXES.contains(m);
    }

    public static boolean isAxe(Material m) {
        return AXES.contains(m);
    }

    public static boolean isShovel(Material m) {
        return SHOVELS.contains(m);
    }

    public static boolean isSword(Material m) {
        return SWORDS.contains(m);
    }

    public static boolean isRepairable(Material m) {
        return REPAIRABLE.contains(m);
    }

}
