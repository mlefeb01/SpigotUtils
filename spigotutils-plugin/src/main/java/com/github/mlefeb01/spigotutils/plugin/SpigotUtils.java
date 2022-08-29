package com.github.mlefeb01.spigotutils.plugin;

import com.github.mlefeb01.spigotutils.plugin.command.CmdSpigotUtils;
import com.github.mlefeb01.spigotutils.plugin.command.CmdUpgrade;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemListener;
import com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem.UpdateItemTask;
import com.github.mlefeb01.spigotutils.plugin.gui.GUIListener;
import com.github.mlefeb01.spigotutils.plugin.struct.RegistryListener;

/**
 * SpigotUtils plugin
 *
 * @author Matt Lefebvre
 */
public final class SpigotUtils extends SUPlugin {
    private static SpigotUtils instance;
    private final ConfigYml configYml = new ConfigYml(this);

    @Override
    public void onEnable() {
        instance = this;

        configYml.load();

        // Cmd
        registerCommand("spigotutils", new CmdSpigotUtils(configYml));

        // Registry
        registerListener(new RegistryListener());

        // Custom Item
        registerCommand("upgrade", new CmdUpgrade(configYml));
        registerListener(new CustomItemListener());
        getServer().getScheduler().runTaskTimer(this, new UpdateItemTask(), 0L, 100L);

        // GUI
        registerListener(new GUIListener());
    }

    /**
     * Returns the SpigotUtils plugin instance
     *
     * @return instance
     */
    public static SpigotUtils getInstance() {
        return instance;
    }

}
