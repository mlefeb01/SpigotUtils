package com.github.mlefeb01.spigotutils;

import com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.customitem.CustomItemRegistry;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Base plugin
 * @author Matt Lefebvre
 */
public abstract class SUPlugin extends JavaPlugin {

    /**
     * Registers a {@link Listener}
     * @param listener listener
     */
    protected void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Registers a command
     * @param command command
     * @param commandExecutor commandExecutor
     */
    protected void registerCommand(String command, CommandExecutor commandExecutor) {
        getCommand(command).setExecutor(commandExecutor);
    }

    /**
     * Registers a command and a tab completer
     * @param command command
     * @param commandExecutor commandExecutor
     * @param tabCompleter tabCompleter
     */
    protected void registerCommand(String command, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        registerCommand(command, commandExecutor);
        getCommand(command).setTabCompleter(tabCompleter);
    }

    /**
     * Registers a custom item
     * @param customItem custom item
     */
    protected void registerCustomItem(AbstractCustomItem customItem) {
        CustomItemRegistry.registerCustomItem(customItem);
    }

}
