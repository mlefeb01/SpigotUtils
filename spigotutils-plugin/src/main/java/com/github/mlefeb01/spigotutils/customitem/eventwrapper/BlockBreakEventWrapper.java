package com.github.mlefeb01.spigotutils.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem} when a block is broken with the mentioned item
 * @author Matt Lefebvre
 */
public class BlockBreakEventWrapper implements IEventWrapper {
    @Getter
    private final BlockBreakEvent event;
    @Getter
    private final NBTItem nbtItem;

    public BlockBreakEventWrapper(BlockBreakEvent event, NBTItem nbtItem) {
        this.event = event;
        this.nbtItem = nbtItem;
    }

}
