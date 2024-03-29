package com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem;

import com.github.mlefeb01.spigotutils.api.utils.ItemUtils;
import com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemRegistry;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Task that updates {@link AbstractUpgradableItem} meta
 *
 * @author Matt Lefebvre
 */
public final class UpdateItemTask implements Runnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!player.isOnline() || player.isDead()) {
                return;
            }

            final ItemStack item = player.getItemInHand();
            if (ItemUtils.isNullOrAir(item)) {
                return;
            }

            final NBTItem nbtItem = new NBTItem(item);
            if (!AbstractCustomItem.isCustomItem(nbtItem)) {
                return;
            }

            final AbstractCustomItem customItem = CustomItemRegistry.getInstance().get(AbstractCustomItem.getIdFromCustomItem(nbtItem));
            if (!(customItem instanceof AbstractUpgradableItem)) {
                return;
            }

            final AbstractUpgradableItem upgradableItem = (AbstractUpgradableItem) customItem;
            final UUID uuid = UUID.fromString(nbtItem.getString(AbstractUpgradableItem.UPGRADABLE_ITEM_DATA));
            if (!upgradableItem.removeItemUpdate(uuid)) {
                return;
            }

            upgradableItem.updateItemMeta(item, upgradableItem.getItemData(uuid));
        });
    }

}
