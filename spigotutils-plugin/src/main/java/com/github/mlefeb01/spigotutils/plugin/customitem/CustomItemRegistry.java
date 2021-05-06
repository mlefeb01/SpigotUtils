package com.github.mlefeb01.spigotutils.plugin.customitem;

import com.github.mlefeb01.spigotutils.plugin.struct.AbstractRegistry;

/**
 * Registry for {@link AbstractCustomItem}. All custom items associated with a plugin will be removed automatically when
 * the plugin is disabled, but you can optionally unregister the item before that occurs.
 * @author Matt Lefebvre
 */
public final class CustomItemRegistry extends AbstractRegistry<AbstractCustomItem> {
    private static final CustomItemRegistry instance = new CustomItemRegistry();

    private CustomItemRegistry() {}

    public static CustomItemRegistry getInstance() {
        return instance;
    }

}
