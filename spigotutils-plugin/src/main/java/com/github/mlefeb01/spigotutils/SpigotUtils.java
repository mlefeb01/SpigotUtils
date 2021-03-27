package com.github.mlefeb01.spigotutils;
import com.github.mlefeb01.spigotutils.customitem.CustomItemListener;
import com.github.mlefeb01.spigotutils.gui.GUIListener;

/**
 * SpigotUtils plugin
 * @author Matt Lefebvre
 */
public final class SpigotUtils extends SUPlugin {
    private static SpigotUtils i;

    @Override
    public void onEnable() {
        i = this;
        registerListener(new CustomItemListener());
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
