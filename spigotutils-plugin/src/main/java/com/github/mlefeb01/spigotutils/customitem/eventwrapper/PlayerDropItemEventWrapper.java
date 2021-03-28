package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player drops the item
 * @author Matt Lefebvre
 */
public class PlayerDropItemEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerDropItemEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public PlayerDropItemEventWrapper(PlayerDropItemEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
