package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when the item is damaged (e.g. - burn)
 * @author Matt Lefebvre
 */
public class EntityDamageEventWrapper implements IEventWrapper {
    @Getter
    private final EntityDamageEvent event;
    @Getter
    private final NBTItem nbtItem;

    public EntityDamageEventWrapper(EntityDamageEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
