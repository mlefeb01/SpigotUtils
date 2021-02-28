package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.ItemDespawnEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item despawns
 * @author Matt Lefebvre
 */
public class ItemDespawnEventWrapper implements IEventWrapper {
    @Getter
    private ItemDespawnEvent event;
    @Getter
    private NBTItem nbtItem;

    public ItemDespawnEventWrapper(ItemDespawnEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
