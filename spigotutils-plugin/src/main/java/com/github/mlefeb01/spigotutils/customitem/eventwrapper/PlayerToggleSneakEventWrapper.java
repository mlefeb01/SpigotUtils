package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a player toggles sneak holding the item
 * @author Matt Lefebvre
 */
public class PlayerToggleSneakEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerToggleSneakEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PlayerToggleSneakEventWrapper(PlayerToggleSneakEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
