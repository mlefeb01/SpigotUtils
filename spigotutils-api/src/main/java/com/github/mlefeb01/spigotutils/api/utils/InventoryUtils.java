package com.github.mlefeb01.spigotutils.api.utils;

import com.github.mlefeb01.spigotutils.api.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Utility methods for common Inventory operations
 * @author Matt Lefebvre
 */
public final class InventoryUtils {

    private InventoryUtils() {
        throw new AssertionError();
    }

    /**
     * Fills all empty slots in an inventory with the filler item
     * @param inventory inventory
     * @param filler filler
     */
    public static void fillEmpty(Inventory inventory, ItemStack filler) {
        for (int n = 0; n < inventory.getSize(); n++) {
            if (!ItemUtils.isNullOrAir(inventory.getItem(n))) {
                continue;
            }
            inventory.setItem(n, filler);
        }
    }

    /**
     * Fills ALL slots in an inventory with the filler item
     * @param inventory inventory
     * @param filler filler
     */
    public static void fillAll(Inventory inventory, ItemStack filler) {
        for (int n = 0; n < inventory.getSize(); n++) {
            inventory.setItem(n, filler);
        }
    }

    /**
     * Fills the "corners" of an inventory with the filler item. The corners of an inventory includes every slot in the
     * first/last row and the first/last slot of the rows in between
     * @param inventory inventory
     * @param filler filler
     */
    public static void fillCorners(Inventory inventory, ItemStack filler) {
        final int size = inventory.getSize();
        if (size < 27) {
            throw new IllegalStateException("InventoryUtils#fillCorners requires the inventory to have 3 or more rows!");
        }

        // top/bottom
        for (int n = 0; n < 9; n++) {
            inventory.setItem(n, filler);
            // -1 because getSize() is 1 greater than the actual slot, think arrays/indexes
            inventory.setItem(size - n - 1, filler);
        }

        // sides
        for (int n = 9; n < size - 9; n += 9) {
            inventory.setItem(n, filler);
            inventory.setItem(n + 8, filler);
        }
    }

    /**
     * Fills an inventory entirely with colored glass panes
     * @param inventory inventory
     * @param one color one
     * @param two color two
     * @param paneGlow should the panes glow
     */
    public static void fillWithColoredPanes(Inventory inventory, short one, short two, boolean paneGlow) {
        final ItemStack paneOne = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(one).name(" ").lore(Arrays.asList(" ")).glow(paneGlow).build();
        final ItemStack paneTwo = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(two).name(" ").lore(Arrays.asList(" ")).glow(paneGlow).build();

        int count = 0;
        for (int n = 0; n < inventory.getSize(); n++) {
            inventory.setItem(n, count % 2 == 0 ? paneOne : paneTwo);
            count++;
        }
    }

    /**
     * Swaps the colors of glass panes in an inventory, can be used in combination with InventoryUtils#fillWithColoredPanes
     * to easily have animated inventories
     * @param inventory inventory
     * @param one one
     * @param two two
     */
    public static void swapPaneColor(Inventory inventory, short one, short two) {
        for (int n = 0; n < inventory.getSize(); n++) {
            final ItemStack item = inventory.getItem(n);
            if (ItemUtils.isNullOrAir(item) || item.getType() != Material.STAINED_GLASS_PANE) {
                continue;
            }

            if (item.getDurability() == one) {
                item.setDurability(two);
            } else if (item.getDurability() == two) {
                item.setDurability(one);
            }
        }
    }

}
