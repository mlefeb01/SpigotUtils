package com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Event wrapper for {@link com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem} when a block is broken with the mentioned item
 *
 * @author Matt Lefebvre
 */
public class BlockBreakEventWrapper implements IEventWrapper {
    @Getter
    private final BlockBreakEvent event;
    @Getter
    private final ItemStack item;
    @Getter
    private final NBTItem nbtItem;
    @Getter
    private final List<ItemStack> drops;

    public BlockBreakEventWrapper(BlockBreakEvent event, ItemStack item, NBTItem nbtItem, List<ItemStack> drops) {
        this.event = event;
        this.item = item;
        this.nbtItem = nbtItem;
        this.drops = drops;
    }

}
