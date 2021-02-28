package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is equipped/unequipped
 * @author Matt Lefebvre
 */
public class PlayerItemHeldEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerItemHeldEvent event;
    @Getter
    private final NBTItem oldItem;
    @Getter
    private final NBTItem newItem;
    @Getter
    private final SwitchReason reason;

    public PlayerItemHeldEventWrapper(PlayerItemHeldEvent event, NBTItem oldItem, NBTItem newItem, SwitchReason reason) {
        this.event = event;
        this.oldItem = oldItem;
        this.newItem  = newItem;
        this.reason = reason;
    }

    public static enum SwitchReason {
        EQUIPPING,
        UNEQUIPPING
    }

}
