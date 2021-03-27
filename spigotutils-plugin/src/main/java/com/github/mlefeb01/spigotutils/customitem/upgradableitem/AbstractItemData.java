package com.github.mlefeb01.spigotutils.customitem.upgradableitem;

import lombok.Getter;

import java.util.UUID;

/**
 * Base class for upgradable item data
 * @author Matt Lefebvre
 */
public abstract class AbstractItemData {
    @Getter
    private final UUID originalOwner;

    /**
     * Constructor
     * @param originalOwner the original owner of the tool
     */
    public AbstractItemData(UUID originalOwner) {
        this.originalOwner = originalOwner;
    }

}
