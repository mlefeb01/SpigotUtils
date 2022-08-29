package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} when an entity is killed with the item
 *
 * @author Matt Lefebvre
 */
public class EntityDeathEventWrapper implements IEventWrapper {
    @Getter
    private final EntityDeathEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;

    public EntityDeathEventWrapper(EntityDeathEvent event, ItemStack item, NBTItem nbtItem) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
    }

}
