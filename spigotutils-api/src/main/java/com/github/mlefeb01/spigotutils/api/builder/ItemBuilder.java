package com.github.mlefeb01.spigotutils.api.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Builder class for {@link ItemStack}
 *
 * @author https://github.com/Yona168
 */
public class ItemBuilder {
    private ItemMeta meta;
    private ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta();
    }

    public static ItemBuilder copyOf(ItemStack itemStack) {
        return new ItemBuilder(new ItemStack(itemStack));
    }

    public static ItemStack of(Material material, int itemId, int amount, String name, List<String> lore) {
        return new ItemBuilder(new ItemStack(material, amount, (short) itemId)).name(name).lore(lore).build();
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder type(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder name(String name) {
        if (name == null) {
            return this;
        }
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder itemMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    public ItemBuilder durability(short dura) {
        this.itemStack.setDurability(dura);
        return this;
    }

    public ItemBuilder data(MaterialData data) {
        this.itemStack.setData(data);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        lore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments, boolean safe) {
        if (safe) itemStack.addEnchantments(enchantments);
        else itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level, boolean safe) {
        if (safe) itemStack.addEnchantment(enchantment, level);
        else itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}