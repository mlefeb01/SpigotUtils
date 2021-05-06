package com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem;

import lombok.Getter;

import java.util.UUID;

/**
 * Template for {@link AbstractUpgradableItem} persistent data
 * @author Matt Lefebvre
 */
public abstract class AbstractItemData {
    @Getter
    private final UUID originalOwner;

    /**
     * Constructor
     * @param originalOwner the original owner of the upgradable item
     */
    public AbstractItemData(UUID originalOwner) {
        this.originalOwner = originalOwner;
    }

}
