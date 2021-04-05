package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is equipped/unequipped
 * @author Matt Lefebvre
 */
public class PlayerItemHeldEventWrapper implements IEventWrapper {
    @Getter
    private final PlayerItemHeldEvent event;
    @Getter
    private final ItemStack oldItem;
    @Getter
    private final ItemStack newItem;
    @Getter
    private final NBTItem oldNbt;
    @Getter
    private final NBTItem newNbt;
    @Getter
    private final SwitchReason reason;

    public PlayerItemHeldEventWrapper(PlayerItemHeldEvent event, ItemStack oldItem, ItemStack newItem, NBTItem oldNbt, NBTItem newNbt, SwitchReason reason) {
        this.event = event;
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.oldNbt = oldNbt;
        this.newNbt = newNbt;
        this.reason = reason;
    }

    public static enum SwitchReason {
        EQUIPPING,
        UNEQUIPPING
    }

}
