package com.github.mlefeb01.spigotutils.gui;

import com.github.mlefeb01.spigotutils.SpigotUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

/**
 * Handles inventory events for bukkit inventories wrapped with {@link GUI}
 * @author Matt Lefebvre
 */
public final class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final GUI gui = GUI.get(event.getInventory());
        if (gui == null) {
            return;
        }

        final Inventory clicked = event.getClickedInventory();
        if (clicked == null) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        if (event.getView().getBottomInventory().equals(clicked)) {
            if (gui.isAllowBottomInventory()) {
                event.setCancelled(false);
                event.setResult(Event.Result.DEFAULT);
            }
            return;
        }

        final GUIAction action = gui.getAction(event.getSlot());
        if (action == null) {
            return;
        }

        if (gui.isAutoClosing()) {
            Bukkit.getScheduler().runTask(SpigotUtils.getInstance(), player::closeInventory);
        }

        action.activate(event, player);
    }

    // Prevents dragging items into empty slots, this probably could be supported but it is not at this time
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        final GUI gui = GUI.get(event.getInventory());
        if (gui == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        // No need to check if the GUI exists, would require querying the map 2x. Instead just query once (remove) and let the internal map decide what to do
        Bukkit.getScheduler().runTask(SpigotUtils.getInstance(), () -> GUI.remove(event.getInventory()));
    }

}
