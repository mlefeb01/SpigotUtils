package com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.plugin.ConfigYml;
import com.github.mlefeb01.spigotutils.plugin.currency.Currency;
import com.github.mlefeb01.spigotutils.plugin.gui.GUIAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ActionPurchaseUpgrade implements GUIAction {
    private final ConfigYml configYml;
    private final AbstractUpgradableItem upgradableItem;
    private final AbstractItemUpgrade upgrade;
    private final AbstractItemData itemData;

    public ActionPurchaseUpgrade(ConfigYml configYml, AbstractUpgradableItem upgradableItem, AbstractItemUpgrade upgrade, AbstractItemData itemData) {
        this.configYml = configYml;
        this.upgradableItem = upgradableItem;
        this.upgrade = upgrade;
        this.itemData = itemData;
    }

    @Override
    public boolean activate(InventoryClickEvent event, Player player) {
        final UpgradeMeta upgradeMeta = upgrade.getUpgradeMeta();
        final int currentLevel = upgrade.getUpgradeLevel(itemData);
        if (currentLevel >= upgradeMeta.getMaxLevel()) {
            return false;
        }

        final int nextLevel = currentLevel + 1;

        final long cost = CostScaling.calculateUpgradeCost(upgradeMeta, currentLevel);

        final Currency currency = upgrade.getUpgradeMeta().getCurrency();
        if (!currency.has(player, cost)) {
            player.sendMessage(configYml.getMessageCantAfford()
                    .replace("%remaining%", String.format("%,d", cost - currency.get(player)))
                    .replace("%symbol%", currency.getCurrencySymbol())
            );
            return false;
        }

        currency.remove(player, cost);
        event.getInventory().setItem(upgrade.getUpgradeMeta().getUpgradeMenuSlot(), upgrade.formatUpgradeMenuItem(nextLevel));
        upgrade.setUpgradeLevel(itemData, nextLevel);
        upgradableItem.updateItemMeta(player.getItemInHand(), itemData);
        return true;
    }

}
