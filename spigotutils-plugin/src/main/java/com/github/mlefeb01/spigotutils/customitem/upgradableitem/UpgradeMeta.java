package com.github.mlefeb01.spigotutils.customitem.upgradableitem;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

/**
 * Stores important meta associated with a {@link AbstractItemUpgrade}
 * @author Matt Lefebvre
 */
public class UpgradeMeta {
    @Getter
    private boolean enabled;
    @Getter
    private final int upgradeMenuSlot;
    @Getter
    private final ItemStack upgradeMenuItem;
    @Getter
    private final double valuePerLevel;
    @Getter
    private final long cost;
    @Getter
    private final int maxLevel;
    @Getter
    private final CostScaling costScaling;

    /**
     * Constructor
     * @param enabled if the upgrade is enabled or not
     * @param upgradeMenuSlot the slot the upgrades display item will appear in the items respective upgrade menu (not the main menu)
     * @param upgradeMenuItem the actual item that will be displayed
     * @param valuePerLevel the value per level the upgrade will gain
     * @param cost the cost of the upgrade (scales accordingly with costScaling)
     * @param maxLevel the max level of the upgrade
     * @param costScaling scaling behavior of the upgrade cost
     */
    public UpgradeMeta(boolean enabled, int upgradeMenuSlot, ItemStack upgradeMenuItem, double valuePerLevel, long cost, int maxLevel, CostScaling costScaling) {
        this.upgradeMenuSlot = upgradeMenuSlot;
        this.upgradeMenuItem = upgradeMenuItem;
        this.valuePerLevel = valuePerLevel;
        this.cost = cost;
        this.maxLevel = maxLevel;
        this.costScaling = costScaling;
    }

}
