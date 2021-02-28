package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is used as a recipe item in a crafting inventory
 * @author Matt Lefebvre
 */
public class PrepareItemCraftEventWrapper implements IEventWrapper {
    @Getter
    private final PrepareItemCraftEvent event;
    @Getter
    private final NBTItem nbtItem;

    public PrepareItemCraftEventWrapper(PrepareItemCraftEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
