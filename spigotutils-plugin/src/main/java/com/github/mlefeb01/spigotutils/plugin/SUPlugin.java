package com.github.mlefeb01.spigotutils.plugin;

import com.github.mlefeb01.spigotutils.plugin.currency.Currency;
import com.github.mlefeb01.spigotutils.plugin.currency.CurrencyRegistry;
import com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemRegistry;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Base plugin
 *
 * @author Matt Lefebvre
 */
public abstract class SUPlugin extends JavaPlugin {

    /**
     * Registers a {@link Listener}
     *
     * @param listener listener
     */
    protected void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Registers a command
     *
     * @param command         command
     * @param commandExecutor commandExecutor
     */
    protected void registerCommand(String command, CommandExecutor commandExecutor) {
        getCommand(command).setExecutor(commandExecutor);
    }

    /**
     * Registers a command and a tab completer
     *
     * @param command         command
     * @param commandExecutor commandExecutor
     * @param tabCompleter    tabCompleter
     */
    protected void registerCommand(String command, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        registerCommand(command, commandExecutor);
        getCommand(command).setTabCompleter(tabCompleter);
    }

    /**
     * Registers a custom item
     *
     * @param plugin     plugin
     * @param customItem custom item
     */
    protected void registerCustomItem(Plugin plugin, AbstractCustomItem customItem) {
        CustomItemRegistry.getInstance().register(plugin, customItem);
    }

    /**
     * Registers a currency
     *
     * @param plugin   plugin
     * @param currency currency
     */
    protected void registerCurrency(Plugin plugin, Currency currency) {
        CurrencyRegistry.getInstance().register(plugin, currency);
    }

}
