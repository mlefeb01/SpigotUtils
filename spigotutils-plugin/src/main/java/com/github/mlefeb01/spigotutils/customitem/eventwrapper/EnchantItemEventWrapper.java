package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.enchantment.EnchantItemEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is enchanted
 * @author Matt Lefebvre
 */
public class EnchantItemEventWrapper implements IEventWrapper {
    @Getter
    private final EnchantItemEvent event;
    @Getter
    private final NBTItem nbtItem;

    public EnchantItemEventWrapper(EnchantItemEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
