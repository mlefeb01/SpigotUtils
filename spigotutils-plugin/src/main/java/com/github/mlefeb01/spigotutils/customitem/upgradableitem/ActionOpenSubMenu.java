package com.github.mlefeb01.spigotutils.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.SpigotUtils;
import com.github.mlefeb01.spigotutils.api.utils.ItemUtils;
import com.github.mlefeb01.spigotutils.ConfigYml;
import com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.gui.GUI;
import com.github.mlefeb01.spigotutils.gui.GUIAction;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ActionOpenSubMenu implements GUIAction {
    private final ConfigYml configYml;
    private final AbstractUpgradableItem upgradableItem;

    public ActionOpenSubMenu(ConfigYml configYml, AbstractUpgradableItem upgradableItem) {
        this.configYml = configYml;
        this.upgradableItem = upgradableItem;
    }

    @Override
    public boolean activate(InventoryClickEvent event, Player player) {
        final ItemStack clicked = event.getCurrentItem();
        if (ItemUtils.isNullOrAir(clicked)) {
            return false;
        }

        final ItemStack playerItem = player.getItemInHand();
        if (ItemUtils.isNullOrAir(playerItem)) {
            player.sendMessage(configYml.getNotHoldingUpgradableItemMessage());
            return false;
        }

        final NBTItem playerNbt = new NBTItem(playerItem);
        if (!playerNbt.hasKey(AbstractCustomItem.CUSTOM_ITEM_NBT)) {
            player.sendMessage(configYml.getNotHoldingUpgradableItemMessage());
            return false;
        }

        if (!playerNbt.getString(AbstractCustomItem.CUSTOM_ITEM_NBT).equals(upgradableItem.getName())) {
            player.sendMessage(configYml.getDifferentUpgradableItemMessage());
            return false;
        }

        final AbstractItemData itemData = upgradableItem.getItemData(UUID.fromString(playerNbt.getString(AbstractUpgradableItem.UPGRADABLE_ITEM_DATA)));
        if (itemData == null) {
            player.sendMessage(configYml.getNoItemDataMessage());
            return false;
        }

        final GUI gui = configYml.getSubUpgradeMenu(player, upgradableItem, itemData);
        Bukkit.getScheduler().runTask(SpigotUtils.getInstance(), () -> gui.open(player));
        return true;
    }

}
