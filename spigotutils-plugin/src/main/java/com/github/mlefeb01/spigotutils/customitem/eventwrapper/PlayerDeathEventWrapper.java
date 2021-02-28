package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player dies holding the item
 * @author Matt Lefebvre
 */
public class PlayerDeathEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerDeathEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerDeathEventWrapper(PlayerDeathEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
