package com.github.mlefeb01.spigotutils;

import com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.customitem.CustomItemRegistry;
import com.github.mlefeb01.spigotutils.customitem.CustomItemListener;

/**
 * SpigotUtils plugin
 * @author Matt Lefebvre
 */
public final class SpigotUtils extends SUPlugin {
    private static SpigotUtils i;

    @Override
    public void onEnable() {
        i = this;
        registerListener(new CustomItemListener());
    }

    /**
     * Registers a {@link AbstractCustomItem}
     * @param customItem customItem
     */
    public void registerCustomItem(AbstractCustomItem customItem) {
        CustomItemRegistry.registerCustomItem(customItem);
    }

    /**
     * Returns the SpigotUtils plugin instance
     * @return instance
     */
    public static SpigotUtils getInstance() {
        return i;
    }

}
