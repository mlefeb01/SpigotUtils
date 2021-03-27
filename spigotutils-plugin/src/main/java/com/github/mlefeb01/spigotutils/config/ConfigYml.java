package com.github.mlefeb01.spigotutils.config;

import com.github.mlefeb01.spigotutils.SpigotUtils;
import com.github.mlefeb01.spigotutils.api.builder.ItemBuilder;
import com.github.mlefeb01.spigotutils.api.config.AbstractConfig;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.customitem.CustomItemRegistry;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.AbstractItemUpgrade;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.AbstractUpgradableItem;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.MainUpgradeMenuHandler;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.SubUpgradeMenuHandler;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class ConfigYml extends AbstractConfig {

    public ConfigYml(SpigotUtils plugin) {
        super(plugin, "config.yml");
    }

    // Upgradable Items
    private Inventory upgradeMenu;
    private ItemStack upgradeMenuFiller;

    @Override
    protected void cache() {
        upgradeMenu = null;
        upgradeMenuFiller = config.getBoolean("upgradable-items.filler-item.enabled") ?
                new ItemBuilder(Material.getMaterial(config.getString("upgradable-items.filler-item.material")))
                        .durability((short) config.getInt("upgradable-items.filler-item.item-id"))
                        .name(TextUtils.color(config.getString("upgradable-items.filler-item.name")))
                        .lore(TextUtils.colorList(config.getStringList("upgradable-items.filler-item.lore")))
                        .glow(config.getBoolean("upgradable-items.filler-item.glow"))
                        .build()
                :
                null;
    }

    /*
    Lazy load the upgrade menu because we can't initialize it in #cache. This is because on first boot, SpigotUtils will
    load before all plugins that depend on it meaning that this config will load before any custom items are registered.
    To work around that, we will simply create the menu once /upgrade is executed for the first time (after each #cache call).
    Also, this menu is static (i.e. - its contents are always the same for every player) so we can reuse the same Inventory instance
     */
    public Inventory getMainUpgradeMenu() {
        if (upgradeMenu != null) {
            return upgradeMenu;
        }

        final Inventory menu = new MainUpgradeMenuHandler(config.getInt("upgradable-items.upgrade-menu.size"), TextUtils.color(config.getString("upgradable-items.upgrade-menu.title"))).getInventory();

        if (upgradeMenuFiller != null) {
            for (int n = 0; n < menu.getSize(); n++) {
                menu.setItem(n, upgradeMenuFiller);
            }
        }

        for (AbstractCustomItem customItem : CustomItemRegistry.getAllCustomItems()) {
            if (!(customItem instanceof AbstractUpgradableItem)) {
                continue;
            }

            final AbstractUpgradableItem upgradableItem = (AbstractUpgradableItem) customItem;
            final NBTItem nbtItem = new NBTItem(upgradableItem.getUpgradeMenuDisplayItem());
            nbtItem.setString(MainUpgradeMenuHandler.UPGRADE_ITEM_NBT, upgradableItem.getIdentifier());
            menu.setItem(upgradableItem.getUpgradeMenuSlot(), nbtItem.getItem());
        }

        upgradeMenu = menu;
        return upgradeMenu;
    }

    public Inventory getSubUpgradeMenu(AbstractUpgradableItem upgradableItem) {
        final Inventory menu = new SubUpgradeMenuHandler(upgradableItem.getUpgradeMenuSize(), upgradableItem.getUpgradeMenuTitle()).getInventory();

        if (upgradeMenuFiller != null) {
            for (int n = 0; n < menu.getSize(); n++) {
                menu.setItem(n, upgradeMenuFiller);
            }
        }

        for (AbstractItemUpgrade upgrade : upgradableItem.getUpgrades()) {
            final NBTItem nbtItem = new NBTItem(upgrade.getUpgradeMeta().getUpgradeMenuItem());
            nbtItem.setString();
        }

        return menu;
    }

}
