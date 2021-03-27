package com.github.mlefeb01.spigotutils.gui;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Wraps a Bukkit inventory and provides additional functionality. The action system is inspired by MassiveCore, it
 * maps menu slots to an action that will be ran when clicked. This allows for easy interactive menu creation without
 * the hassle of identifying different ItemStacks within the menu
 * @author Matt Lefebvre
 */
public class GUI {
    private static final Map<Inventory, GUI> GUIS = new HashMap<>();
    private final Inventory inventory;
    private final GUIAction[] actions;
    private boolean autoClosing;
    private boolean allowBottomInventory;

    /**
     * Constructor
     * @param inventory the Bukkit inventory
     */
    public GUI(Inventory inventory) {
        if (inventory == null) {
            throw new NullPointerException("Inventory must be nonnull");
        }

        this.inventory = inventory;
        this.actions = new GUIAction[inventory.getSize()];
        this.allowBottomInventory = false;
        this.allowBottomInventory = false;

        GUI.GUIS.put(inventory, this);
    }

    /**
     * Getter method for the Bukkit inventory associated with this GUI
     * @return  inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns the action defined for a slot
     * @param slot slot
     * @return action or null
     */
    public GUIAction getAction(int slot) {
        return (slot < 0 || slot >= this.actions.length) ? null : this.actions[slot];
    }

    /**
     * Sets the action that will be executed when a slot is clicked
     * @param slot slot
     * @param action action
     */
    public void setAction(int slot, GUIAction action) {
        if (slot < 0 || slot >=  this.actions.length) {
            return;
        }
        this.actions[slot] = action;
    }

    /**
     * Sets autoClosing for this GUI
     * @param autoClosing autoClosing
     */
    public void setAutoClosing(boolean autoClosing) {
        this.autoClosing = autoClosing;
    }

    /**
     * Should this GUI be closed after the first click?
     * @return autoClosing
     */
    public boolean isAutoClosing() {
        return autoClosing;
    }

    /**
     * Should the player be allowed to edit the bottom inventory while this GUI is opened
     * @return allowBottomInventory
     */
    public boolean isAllowBottomInventory() {
        return allowBottomInventory;
    }

    /**
     * Sets allowBottomInventory for this GUI
     * @param allowBottomInventory allowBottomInventory
     */
    public void setAllowBottomInventory(boolean allowBottomInventory) {
        this.allowBottomInventory = allowBottomInventory;
    }

    /**
     * Returns the GUI associated with the given inventory, or null
     * @param inventory inventory
     * @return gui or null
     */
    public static GUI get(Inventory inventory) {
        return GUI.GUIS.get(inventory);
    }

    /**
     * Used to unregister a GUI when a player closes the inventory associated with it
     * @param inventory inventory
     */
    public static void remove(Inventory inventory) {
        GUI.GUIS.remove(inventory);
    }

}
