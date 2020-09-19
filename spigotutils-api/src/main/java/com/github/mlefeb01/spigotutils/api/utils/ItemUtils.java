package com.github.mlefeb01.spigotutils.api.utils;

import com.github.mlefeb01.spigotutils.api.constants.MaterialConstants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility methods for common {@link ItemStack} operations
 *
 * @author Matt Lefebvre
 */
public final class ItemUtils {

    private ItemUtils() {
        throw new AssertionError();
    }

    /**
     * Checks whether an ItemStack is null or air
     *
     * @param item item
     * @return null or air
     */
    public static boolean isNullOrAir(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    /**
     * Applies a dumby glow effect to an item by enchanting it with luck and adding the hide enchants
     *
     * @param item item
     * @return glowing item
     */
    public static ItemStack addGlow(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Undoes ItemUtils.addGlow(item)
     *
     * @param item item
     * @return removed glow item
     */
    public static ItemStack removeGlow(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemMeta.removeEnchant(Enchantment.LUCK);

        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Multiplies every item in a list by a multiplier
     * @param items list of items
     * @param multiplier multiplier
     * @return items with their amount multiplied
     */
    public static List<ItemStack> multiplyItemAmounts(List<ItemStack> items, double multiplier) {
        items.forEach(item -> item.setAmount((int) (item.getAmount() * multiplier)));
        return items;
    }

    /**
     * Calculates a drop multiplier given a fortune level. Calculations based off of vanilla fortune rates
     * https://minecraft.gamepedia.com/Fortune
     *
     * @param fortuneLevel fortune level
     * @return drop multiplier
     */
    public static double getFortuneMultiplier(int fortuneLevel) {
        if (fortuneLevel == 0) {
            return 1;
        }

        final int r = ThreadLocalRandom.current().nextInt(0, 100);
        if (fortuneLevel == 1) {
            return (r < 66) ? 1.0 : 2.0;
        } else if (fortuneLevel == 2) {
            return (r < 50) ? 1.0 : (r < 75) ? 2.0 : 3.0;
        } else if (fortuneLevel == 3) {
            return (r < 40) ? 1.0 : (r < 60) ? 2.0 : (r > 80) ? 3.0 : 4.0;
        } else {
            return (r < 40) ? (double) fortuneLevel - 3 : (r < 60) ? (double) fortuneLevel - 2 : (r < 80) ? (double) fortuneLevel - 1 : (double) fortuneLevel;
        }
    }

    /**
     * Fully repairs an item if the item is repairable and has missing durability
     *
     * @param item item
     * @return if the item was able to be repaired
     */
    public static boolean repairItem(ItemStack item) {
        return repairItem(item, 100);
    }

    /**
     * Restores a percentage of an items missing durability if the item is both repairable and has missing durability
     * precondition: percent is greater than 0 and less than or equal to 100
     *
     * @param item item
     * @param percent the % of missing durability to repair
     * @return if the item was able to be repaired
     */
    public static boolean repairItem(ItemStack item, double percent) {
        if (percent > 100 || percent <= 0 || ItemUtils.isNullOrAir(item) || !MaterialConstants.isRepairable(item.getType()) || item.getDurability() == 0) {
            return false;
        }

        item.setDurability((short) (item.getDurability() - (item.getDurability() * (percent/100))));
        return true;
    }

    /**
     * Sets an items durability to a % of its max if it is repairable
     * precondition: percent is greater than 0 and less than or equal to 100
     *
     * @param item item
     * @param percent the percentage of the items max durability to set its current durability to
     * @return if the item's durability was successfully updated
     */
    public static boolean setItemDurability(ItemStack item, double percent) {
        if (percent <= 0 || percent > 100 || ItemUtils.isNullOrAir(item) || !MaterialConstants.isRepairable(item.getType())) {
            return false;
        }

        item.setDurability((short) (item.getType().getMaxDurability() * ((100 - percent)/100)));
        return true;
    }

}
