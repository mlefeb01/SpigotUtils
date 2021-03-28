package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item merges with another item
 * @author Matt Lefebvre
 */
public class ItemMergeEventWrapper implements IEventWrapper {
    @Getter
    private final ItemMergeEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public ItemMergeEventWrapper(ItemMergeEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
