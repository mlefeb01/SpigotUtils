package com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.api.utils.ItemUtils;
import com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper.IEventWrapper;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Extension of {@link AbstractCustomItem} that provides functionality for upgradable items. This is useful for custom items
 * that are more complex then regular custom items, generally having the following attributes
 * - Upgrades
 * - Updating meta (varying frequencies)
 * - Persistent data not stored on the item
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractUpgradableItem extends AbstractCustomItem {
    /**
     * The NBT key used to map the items data uuid to the item
     */
    public static final String UPGRADABLE_ITEM_DATA = "su-uitem-data";
    /**
     * The upgrades that belong to this item
     */
    private final List<AbstractItemUpgrade> upgrades;
    /**
     * The ids of items of this type that need to have their lore updated (UNIQUE TO EACH UPGRADABLE TOOL CLASS)
     */
    private final Set<UUID> itemsToUpdate;

    /**
     * Constructor
     *
     * @param name     name
     * @param upgrades upgrades
     */
    public AbstractUpgradableItem(String name, List<AbstractItemUpgrade> upgrades) {
        super(name);
        this.upgrades = Collections.unmodifiableList(upgrades);
        this.itemsToUpdate = new HashSet<>();
    }

    /**
     * Returns the slot this upgradable items display item will be displayed in the main upgrade menu
     *
     * @return slot
     */
    public abstract int getUpgradeMenuSlot();

    /**
     * Returns the display item that will be displayed in the main upgrade menu for this upgradable item
     *
     * @return display item
     */
    public abstract ItemStack getUpgradeMenuDisplayItem();

    /**
     * Returns the size of this upgradable items upgrade menu size
     *
     * @return size
     */
    public abstract int getUpgradeMenuSize();

    /**
     * Returns the title of this upgradable items menu
     *
     * @return title
     */
    public abstract String getUpgradeMenuTitle();

    /**
     * Creates an instance of this item
     *
     * @param originalOwner the original owner of this item
     * @return item
     */
    public abstract ItemStack getUpgradableItem(UUID originalOwner);

    /**
     * Updates the meta of this item
     *
     * @param item item
     */
    public abstract void updateItemMeta(ItemStack item, AbstractItemData itemData);

    /**
     * Returns the tool data associated given the uuid of an item
     *
     * @return
     */
    public abstract AbstractItemData getItemData(UUID uuid);

    /**
     * Helper method to apply the uuid that is mapped to items data
     *
     * @param nbtItem nbtItem
     * @param uuid    uuid
     */
    protected void applyDataTag(NBTItem nbtItem, UUID uuid) {
        nbtItem.setString(AbstractUpgradableItem.UPGRADABLE_ITEM_DATA, uuid.toString());
    }

    /**
     * Helper method to "activate" the items upgrades
     *
     * @param eventWrapper eventWrapper
     * @param itemData     itemData
     */
    protected void passEventToUpgrades(IEventWrapper eventWrapper, AbstractItemData itemData) {
        for (AbstractItemUpgrade upgrade : upgrades) {
            upgrade.onEvent(eventWrapper, itemData);
        }
    }

    /**
     * Returns the upgrades belonging to this item
     *
     * @return upgrades
     */
    public List<AbstractItemUpgrade> getUpgrades() {
        return this.upgrades;
    }

    /**
     * Adds an items id to the collection of tools that will have their lore updated
     *
     * @param uuid uuid
     * @return false if the uuid is already present, true otherwise
     */
    public boolean queueItemUpdate(UUID uuid) {
        return this.itemsToUpdate.add(uuid);
    }

    /**
     * Removes an items id from the collection of tools that will have their lore updated
     *
     * @param uuid uuid
     * @return boolean if the uuid was in the map
     */
    public boolean removeItemUpdate(UUID uuid) {
        return this.itemsToUpdate.remove(uuid);
    }

    /**
     * Checks if an itemstack is an upgradable item
     *
     * @param item item
     * @return boolean
     */
    public static boolean isUpgradableItem(ItemStack item) {
        if (ItemUtils.isNullOrAir(item)) {
            return false;
        }
        return new NBTItem(item).hasKey(UPGRADABLE_ITEM_DATA);
    }

    /**
     * Checks if an nbtitem is an upgradable item
     *
     * @param nbtItem nbtItem
     * @return boolean
     */
    public static boolean isUpgradableItem(NBTItem nbtItem) {
        return nbtItem.hasKey(UPGRADABLE_ITEM_DATA);
    }

    /**
     * Gets the data UUID from an upgradable item. Should only be called after isUpgradableItem
     *
     * @param item item
     * @return uuid
     */
    public static UUID getDataUUIDFromUpgradableItem(ItemStack item) {
        return UUID.fromString(new NBTItem(item).getString(UPGRADABLE_ITEM_DATA));
    }

    /**
     * Gets the data UUID from an upgradable item. Should only be called after isUpgradableItem
     *
     * @param nbtItem nbtItem
     * @return uuid
     */
    public static UUID getDataUUIDFromUpgradableItem(NBTItem nbtItem) {
        return UUID.fromString(nbtItem.getString(UPGRADABLE_ITEM_DATA));
    }

}
