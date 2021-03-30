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
    private final int maxLevel;
    @Getter
    private final long cost;
    @Getter
    private final Currency currency;
    @Getter
    private final CostScaling costScaling;


    /**
     * Constructor
     * @param enabled if the upgrade is enabled or not (if false, the associated upgrade will not be displayed in the tools upgrade sub  menu)
     * @param upgradeMenuSlot the slot the upgrades display item will appear in the items respective upgrade menu (not the main menu)
     * @param upgradeMenuItem the actual item that will be displayed
     * @param valuePerLevel the value per level the upgrade will gain
     * @param maxLevel the max level of the upgrade
     * @param cost the cost of the upgrade (scales accordingly with costScaling)
     * @param currency the currency used for this upgrade
     * @param costScaling scaling behavior of the upgrade cost
     */
    public UpgradeMeta(boolean enabled, int upgradeMenuSlot, ItemStack upgradeMenuItem, double valuePerLevel, int maxLevel, long cost, Currency currency, CostScaling costScaling) {
        this.enabled = enabled;
        this.upgradeMenuSlot = upgradeMenuSlot;
        this.upgradeMenuItem = upgradeMenuItem;
        this.valuePerLevel = valuePerLevel;
        this.maxLevel = maxLevel;
        this.cost = cost;
        this.currency = currency;
        this.costScaling = costScaling;
    }

}
