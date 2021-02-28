package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Event wrapper for when {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} are placed in non-result anvil slots
 * @author Matt Lefebvre
 */
public class AnvilEventWrapper implements IEventWrapper {
    @Getter
    private final InventoryClickEvent event;
    @Getter
    private final NBTItem nbtItem;

    public AnvilEventWrapper(InventoryClickEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
