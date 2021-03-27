package com.github.mlefeb01.spigotutils.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class MainUpgradeMenuHandler implements InventoryHolder {
    public static final String UPGRADE_ITEM_NBT = "su-uitem-main";
    private final int size;
    private final String title;

    public MainUpgradeMenuHandler(int size, String title) {
        this.size = size;
        this.title = title;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, size, TextUtils.color(title));
    }

}
