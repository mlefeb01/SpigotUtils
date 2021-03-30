package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamageByEntityEventWrapper implements IEventWrapper {
    @Getter
    private final EntityDamageByEntityEvent event;
    @Getter
    private final NBTItem nbtItem;
    @Getter
    private final ItemStack item;

    public EntityDamageByEntityEventWrapper(EntityDamageByEntityEvent event, NBTItem nbtItem, ItemStack item) {
        this.event = event;
        this.nbtItem = nbtItem;
        this.item = item;
    }

}
