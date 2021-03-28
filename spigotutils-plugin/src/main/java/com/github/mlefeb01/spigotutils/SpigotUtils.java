package com.github.mlefeb01.spigotutils;
import com.github.mlefeb01.spigotutils.config.ConfigYml;
import com.github.mlefeb01.spigotutils.customitem.CustomItemListener;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.UpdateItemTask;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.UpgradeCommand;
import com.github.mlefeb01.spigotutils.gui.GUIListener;

/**
 * SpigotUtils plugin
 * @author Matt Lefebvre
 */
public final class SpigotUtils extends SUPlugin {
    private static SpigotUtils i;
    private final ConfigYml configYml = new ConfigYml(this);

    @Override
    public void onEnable() {
        i = this;
        configYml.load();

        // Custom Item
        registerCommand("upgrade", new UpgradeCommand(configYml));
        registerListener(new CustomItemListener(configYml));
        getServer().getScheduler().runTaskTimer(this, new UpdateItemTask(), 0L, 300L);

        // GUI
        registerListener(new GUIListener());
    }

    /**
     * Returns the SpigotUtils plugin instance
     * @return instance
     */
    public static SpigotUtils getInstance() {
        return i;
    }

}
