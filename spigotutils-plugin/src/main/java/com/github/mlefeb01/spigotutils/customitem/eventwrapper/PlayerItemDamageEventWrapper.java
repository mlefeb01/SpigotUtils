package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemDamageEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is damaged
 * @author Matt Lefebvre
 */
public class PlayerItemDamageEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerItemDamageEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerItemDamageEventWrapper(PlayerItemDamageEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
