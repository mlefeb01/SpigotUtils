package com.github.mlefeb01.spigotutils.plugin.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Interface for GUI actions
 * @author Matt Lefebvre
 */
public interface GUIAction {

    /**
     * Activates this GUIAction
     * @param event the InventoryClickEvent
     * @param player the Player who clicked
     * @return true if successful, false if not
     */
    boolean activate(InventoryClickEvent event, Player player);

}
