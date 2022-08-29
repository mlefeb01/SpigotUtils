package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} when the item is damaged
 *
 * @author Matt Lefebvre
 */
public class PlayerItemDamageEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerItemDamageEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public PlayerItemDamageEventWrapper(PlayerItemDamageEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
