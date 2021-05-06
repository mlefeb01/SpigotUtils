package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for when {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} are placed in non-result anvil slots
 * @author Matt Lefebvre
 */
public class AnvilEventWrapper implements IEventWrapper {
    @Getter
    private final InventoryClickEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public AnvilEventWrapper(InventoryClickEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
