package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.FurnaceBurnEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is used as fuel in a furnace
 * @author Matt Lefebvre
 */
public class FurnaceBurnEventWrapper implements IEventWrapper {
    @Getter
    private final FurnaceBurnEvent event;
    @Getter
    private final NBTItem nbtItem;

    public FurnaceBurnEventWrapper(FurnaceBurnEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
