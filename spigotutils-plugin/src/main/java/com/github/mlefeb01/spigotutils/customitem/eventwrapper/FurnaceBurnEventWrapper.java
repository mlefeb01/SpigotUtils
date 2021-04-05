package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is used as fuel in a furnace
 * @author Matt Lefebvre
 */
public class FurnaceBurnEventWrapper implements IEventWrapper {
    @Getter
    private final FurnaceBurnEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public FurnaceBurnEventWrapper(FurnaceBurnEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
