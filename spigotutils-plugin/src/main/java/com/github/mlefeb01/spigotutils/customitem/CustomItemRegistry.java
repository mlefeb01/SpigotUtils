package com.github.mlefeb01.spigotutils.customitem;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for {@link AbstractCustomItem}
 * @author Matt Lefebvre
 */
public final class CustomItemRegistry {
    private static final Map<String, AbstractCustomItem> CUSTOM_ITEMS = new HashMap<>();

    /**
     * Returns a {@link AbstractCustomItem} if it exists
     * @param identifier identifier
     * @return customItem or null
     */
    public static AbstractCustomItem getCustomItem(String identifier) {
        return CustomItemRegistry.CUSTOM_ITEMS.get(identifier);
    }

    /**
     * Registers a {@link AbstractCustomItem}
     * @param customItem customItem
     */
    public static void registerCustomItem(AbstractCustomItem customItem) {
        CustomItemRegistry.CUSTOM_ITEMS.put(customItem.getIdentifier(), customItem);
    }

}
