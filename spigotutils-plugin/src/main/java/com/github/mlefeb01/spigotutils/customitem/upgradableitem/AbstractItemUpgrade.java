package com.github.mlefeb01.spigotutils.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.customitem.eventwrapper.IEventWrapper;
import org.bukkit.inventory.ItemStack;

/**
 * Models an item upgrade
 * @author Matt Lefebvre
 */
public abstract class AbstractItemUpgrade {

    /**
     * Formats the upgrades display item for the upgrade menu at a particular level
     * @param level level
     * @return display item
     */
    public abstract ItemStack formatUpgradeMenuItem(int level);

    /**
     * Returns the {@link UpgradeMeta} associated with this upgrade
     * @return
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
     * @return level
     */
    public abstract int getUpgradeLevel(AbstractItemData itemData);

    /**
     *
     * @param eventWrapper
     * @param itemData
     */
    public void onEvent(IEventWrapper eventWrapper, AbstractItemData itemData) {}

}
