package com.github.mlefeb01.spigotutils.plugin;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemListener;
import com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem.UpdateItemTask;
import com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem.UpgradeCommand;
import com.github.mlefeb01.spigotutils.plugin.gui.GUIListener;
import com.github.mlefeb01.spigotutils.plugin.struct.RegistryListener;

/**
 * SpigotUtils plugin
 * @author Matt Lefebvre
 */
public final class SpigotUtils extends SUPlugin {
    private static SpigotUtils instance;
    private final ConfigYml configYml = new ConfigYml(this);

    @Override
    public void onEnable() {
        instance = this;

        configYml.load();

        // Registry
        registerListener(new RegistryListener());

        // Custom Item
        registerCommand("upgrade", new UpgradeCommand(configYml));
        registerListener(new CustomItemListener());
        getServer().getScheduler().runTaskTimer(this, new UpdateItemTask(), 0L, 100L);

        // GUI
        registerListener(new GUIListener());
    }

    /**
     * Returns the SpigotUtils plugin instance
     * @return instance
     */
    public static SpigotUtils getInstance() {
        return instance;
    }

}