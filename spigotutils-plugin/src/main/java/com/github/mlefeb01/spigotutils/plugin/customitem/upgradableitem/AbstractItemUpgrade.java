package com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper.IEventWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.Collectors;

/**
 * Models an upgrade for {@link AbstractUpgradableItem}
 * @author Matt Lefebvre
 */
public abstract class AbstractItemUpgrade {

    /**
     * Returns the {@link UpgradeMeta} associated with this upgrade
     * @return upgradeMeta
     */
    public abstract UpgradeMeta getUpgradeMeta();

    /**
     * Sets the upgrade level of this upgrade for itemData
     * @param itemData itemData
     * @param level level
     */
    public abstract void setUpgradeLevel(AbstractItemData itemData, int level);

    /**
     * Returns the upgrade level of this upgrade for itemData
     * @param itemData itemData
     * @return level level
     */
    public abstract int getUpgradeLevel(AbstractItemData itemData);

    /**
     * Performs the calculation for this upgrade. What this value represents depends on the context of the upgrade. It could
     * be a chance, a multiplier, etc.
     * @param level level
     * @return calculation
     */
    public abstract double calculate(int level);

    /**
     * Formats the upgrades display item for the upgrade menu at a particular level
     * @param currentLevel current level of the upgrade
     * @return display item for the upgrade
     */
    public ItemStack formatUpgradeMenuItem(int currentLevel) {
        final UpgradeMeta upgradeMeta = getUpgradeMeta();
        final ItemStack item = upgradeMeta.getUpgradeMenuItem().clone();
        final ItemMeta meta = item.getItemMeta();
        final String maxCalculationPlaceholder = String.valueOf(calculate(upgradeMeta.getMaxLevel()));
        meta.setLore(meta.getLore().stream().map(str -> str
                .replace("%level%", String.format("%,d", currentLevel))
                .replace("%max-level%", String.format("%,d", upgradeMeta.getMaxLevel()))
                .replace("%price%", currentLevel >= upgradeMeta.getMaxLevel() ? "MAXED" : String.format("%,d %s", CostScaling.calculateUpgradeCost(upgradeMeta, currentLevel), upgradeMeta.getCurrency().getCurrencySymbol()))
                .replace("%current-calculation%", String.valueOf(calculate(currentLevel)))
                .replace("%next-calculation%", currentLevel == upgradeMeta.getMaxLevel() ? maxCalculationPlaceholder : String.valueOf(calculate(currentLevel + 1)))
                .replace("%max-calculation%", maxCalculationPlaceholder)
        ).collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Activates the upgrade
     * @param eventWrapper eventWrapper
     * @param itemData itemData
     */
    public void onEvent(IEventWrapper eventWrapper, AbstractItemData itemData) {}

}
