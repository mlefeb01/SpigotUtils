package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player interacts with the item
 * @author Matt Lefebvre
 */
public class PlayerInteractEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerInteractEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public PlayerInteractEventWrapper(PlayerInteractEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
