package com.github.mlefeb01.spigotutils.plugin.struct;

import com.github.mlefeb01.spigotutils.plugin.currency.CurrencyRegistry;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public final class RegistryListener implements Listener {

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        final Plugin plugin = event.getPlugin();
        CustomItemRegistry.getInstance().unregisterAll(plugin);
        CurrencyRegistry.getInstance().unregisterAll(plugin);
    }

}
