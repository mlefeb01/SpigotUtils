package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} when a hopper picks up the item
 *
 * @author Matt Lefebvre
 */
public class InventoryPickupItemEventWrapper implements IEventWrapper {
    @Getter
    private final InventoryPickupItemEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public InventoryPickupItemEventWrapper(InventoryPickupItemEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
