package com.github.mlefeb01.spigotutils.plugin;

import com.github.mlefeb01.spigotutils.api.config.AbstractConfig;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemRegistry;
import com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem.*;
import com.github.mlefeb01.spigotutils.plugin.gui.GUI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class ConfigYml extends AbstractConfig {

    public ConfigYml(SpigotUtils plugin) {
        super(plugin, "config.yml");
    }

    // Upgradable Items

    private GUI upgradeMenu;
    private ItemStack upgradeMenuFiller;
    @Getter
    private String notHoldingUpgradableItemMessage;
    @Getter
    private String differentUpgradableItemMessage;
    @Getter
    private String noItemDataMessage;
    @Getter
    private String cantAffordMessage;
    @Getter
    private String notAPlayerMessage;

    // Permissions
    @Getter
    private String permissionUpgrade;
    @Getter
    private String permissionSpigotUtils;
    @Getter
    private String permissionSpigotUtilsHelp;
    @Getter
    private String permissionSpigotUtilsReload;

    @Override
    protected void cache() {
        // Upgradable Items
        if (upgradeMenu != null) {
            // The main upgrade menu has autoRemoving set to false because its shared, so we must remove it manually to prevent a memory leak
            GUI.remove(upgradeMenu.getInventory());
        }
        upgradeMenu = null;
        upgradeMenuFiller = getBoolean("upgradable-items.filler-item.enabled") ? getItemBuilder("upgradable-items.filler-item").build() : null;
        notHoldingUpgradableItemMessage = getColoredString("upgradable-items.not-holding-upgradable-item");
        differentUpgradableItemMessage = getColoredString("upgradable-items.different-upgradable-item");
        noItemDataMessage = getColoredString("upgradable-items.no-item-data");
        cantAffordMessage = getColoredString("upgradable-items.cant-afford");
        notAPlayerMessage = getColoredString("upgradable-items.not-a-player");

        // Permissions
        permissionUpgrade = getString("permissions.upgrade");
        permissionSpigotUtils = getString("permissions.spigotutils");
        permissionSpigotUtilsHelp = getString("permissions.spigotutils-help");
        permissionSpigotUtilsReload = getString("permissions.spigotutils-reload");
    }

    /*
    Lazy load the upgrade menu because we can't initialize it in #cache. This is because on first boot, SpigotUtils will
    load before all plugins that depend on it meaning that this config will load before any custom items are registered.
    To work around that, we will simply create the menu once /upgrade is executed for the first time (after each #cache call).
    Also, this menu is static (i.e. - its contents are always the same for every player) so we can reuse the same Inventory instance
     */
    public GUI getMainUpgradeMenu() {
        if (upgradeMenu != null) {
            return upgradeMenu;
        }

        final Inventory menu = Bukkit.createInventory(null, config.getInt("upgradable-items.upgrade-menu.size"), TextUtils.color(config.getString("upgradable-items.upgrade-menu.title")));

        if (upgradeMenuFiller != null) {
            for (int n = 0; n < menu.getSize(); n++) {
                menu.setItem(n, upgradeMenuFiller);
            }
        }

        final GUI gui = new GUI(menu);
        gui.setAutoRemoving(false);
        gui.setAutoClosing(false);
        gui.setAllowBottomInventory(false);

        for (AbstractCustomItem customItem : CustomItemRegistry.getInstance().getAll()) {
            if (!(customItem instanceof AbstractUpgradableItem)) {
                continue;
            }

            final AbstractUpgradableItem upgradableItem = (AbstractUpgradableItem) customItem;
            final int slot = upgradableItem.getUpgradeMenuSlot();
            menu.setItem(slot, upgradableItem.getUpgradeMenuDisplayItem());
            gui.setAction(slot, new ActionOpenSubMenu(this, upgradableItem));
        }

        upgradeMenu = gui;
        return upgradeMenu;
    }

    // TODO move this to abstractupgradableitem
    public GUI getSubUpgradeMenu(Player player, AbstractUpgradableItem upgradableItem, AbstractItemData itemData) {
        final Inventory menu = Bukkit.createInventory(null, upgradableItem.getUpgradeMenuSize(), upgradableItem.getUpgradeMenuTitle());

        if (upgradeMenuFiller != null) {
            for (int n = 0; n < menu.getSize(); n++) {
                menu.setItem(n, upgradeMenuFiller);
            }
        }

        final GUI gui = new GUI(menu);
        gui.setAutoClosing(false);
        gui.setAllowBottomInventory(false);
        gui.addRunnableOnClose(() -> Bukkit.getScheduler().runTask(SpigotUtils.getInstance(), () -> getMainUpgradeMenu().open(player)));

        for (AbstractItemUpgrade upgrade : upgradableItem.getUpgrades()) {
            final UpgradeMeta upgradeMeta = upgrade.getUpgradeMeta();
            if (!upgradeMeta.isEnabled()) {
                continue;
            }
            final int slot = upgradeMeta.getUpgradeMenuSlot();
            final ItemStack item = upgrade.formatUpgradeMenuItem(upgrade.getUpgradeLevel(itemData));
            menu.setItem(slot, item);
            gui.setAction(slot, new ActionPurchaseUpgrade(this, upgradableItem, upgrade, itemData));
        }

        return gui;
    }

}
