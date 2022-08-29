package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} when a player dies holding the item
 *
 * @author Matt Lefebvre
 */
public class PlayerDeathEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerDeathEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public PlayerDeathEventWrapper(PlayerDeathEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
