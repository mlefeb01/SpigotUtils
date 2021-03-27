package com.github.mlefeb01.spigotutils;
import com.github.mlefeb01.spigotutils.config.ConfigYml;
import com.github.mlefeb01.spigotutils.customitem.CustomItemListener;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.UpgradeCommand;
import com.github.mlefeb01.spigotutils.example.SkeletonSword;
import com.github.mlefeb01.spigotutils.gui.GUIListener;

/**
 * SpigotUtils plugin
 * @author Matt Lefebvre
 */
public final class SpigotUtils extends SUPlugin {
    private static SpigotUtils i;
    private final ConfigYml configYml = new ConfigYml(this);
    private SkeletonSword skeletonSword = new SkeletonSword();

    @Override
    public void onEnable() {
        i = this;
        configYml.load();
        registerCommand("upgrade", new UpgradeCommand(configYml));
        registerListener(new CustomItemListener());
        registerListener(new GUIListener());

        // TODO remove after done testing
        registerCustomItem(skeletonSword);
    }

    /**
     * Returns the SpigotUtils plugin instance
     * @return instance
     */
    public static SpigotUtils getInstance() {
        return i;
    }

}
