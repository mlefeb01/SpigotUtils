package com.github.mlefeb01.spigotutils.example;

import com.github.mlefeb01.spigotutils.api.builder.ItemBuilder;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.AbstractItemData;
import com.github.mlefeb01.spigotutils.customitem.upgradableitem.AbstractUpgradableItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.UUID;

public class SkeletonSword extends AbstractUpgradableItem {

    public SkeletonSword() {
        super("skeletonsword", Collections.emptyList());
    }

    @Override
    public int getUpgradeMenuSlot() {
        return 13;
    }

    @Override
    public ItemStack getUpgradeMenuDisplayItem() {
        return new ItemBuilder(Material.STONE_SWORD).name(TextUtils.color("&8&lSkeleton Sword")).lore(TextUtils.color("&fClick to upgrade...")).glow(true).build();
    }

    @Override
    public int getUpgradeMenuSize() {
        return 9;
    }

    @Override
    public String getUpgradeMenuTitle() {
        return TextUtils.color("&8&lSkeleton Sword Upgrades");
    }

    @Override
    public ItemStack getUpgradableItem(UUID originalOwner) {
        final NBTItem nbtItem = new NBTItem(new ItemBuilder(Material.STONE_SWORD).name(TextUtils.color("&8&lSkeleton Sword")).unbreakable(true).build());
        final UUID uuid = UUID.randomUUID();
        applyIdentifierTag(nbtItem);
        applyDataTag(nbtItem, uuid);
        // TODO map item to data object
        return nbtItem.getItem();
    }

    @Override
    public void updateItemMeta(ItemStack item) {

    }

    @Override
    public AbstractItemData getItemData(UUID uuid) {
        return null;
    }

}
