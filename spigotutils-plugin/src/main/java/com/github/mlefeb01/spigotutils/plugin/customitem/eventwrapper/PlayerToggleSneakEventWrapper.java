package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} when a player toggles sneak holding the item
 *
 * @author Matt Lefebvre
 */
public class PlayerToggleSneakEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerToggleSneakEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public PlayerToggleSneakEventWrapper(PlayerToggleSneakEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
