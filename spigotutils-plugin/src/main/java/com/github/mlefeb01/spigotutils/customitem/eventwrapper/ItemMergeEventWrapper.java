package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.ItemMergeEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item merges with another item
 * @author Matt Lefebvre
 */
public class ItemMergeEventWrapper implements IEventWrapper {
    @Getter
    private final ItemMergeEvent event;
    @Getter
    private final NBTItem nbtItem;

    public ItemMergeEventWrapper(ItemMergeEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
