package com.github.mlefeb01.spigotutils.api.utils;

import com.github.mlefeb01.spigotutils.api.constants.MaterialConstants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility methods for common {@link ItemStack} operations
 *
 * @author Matt Lefebvre
 */
public final class ItemUtils {

    private ItemUtils() {
        throw new AssertionError();
    }

    /**
     * Checks whether an ItemStack is null or air
     *
     * @param item item
     * @return null or air
     */
    public static boolean isNullOrAir(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    /**
     * Applies a dumby glow effect to an item by enchanting it with luck and adding the hide enchants
     *
     * @param item item
     * @return glowing item
     */
    public static ItemStack addGlow(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Undoes ItemUtils.addGlow(item)
     *
     * @param item item
     * @return removed glow item
     */
    public static ItemStack removeGlow(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemMeta.removeEnchant(Enchantment.LUCK);

        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Multiplies every item in a list by a multiplier
     *
     * @param items      list of items
     * @param multiplier multiplier
     * @return items with their amount multiplied
     */
    public static List<ItemStack> multiplyItemAmounts(List<ItemStack> items, double multiplier) {
        items.forEach(item -> item.setAmount((int) (item.getAmount() * multiplier)));
        return items;
    }

    /**
     * Calculates a drop multiplier given a fortune level. Calculations based off of vanilla fortune rates
     * https://minecraft.gamepedia.com/Fortune
     *
     * @param fortuneLevel fortune level
     * @return drop multiplier
     */
    public static double getFortuneMultiplier(int fortuneLevel) {
        if (fortuneLevel == 0) {
            return 1;
        }

        final int r = ThreadLocalRandom.current().nextInt(0, 100);
        if (fortuneLevel == 1) {
            return (r < 66) ? 1.0 : 2.0;
        } else if (fortuneLevel == 2) {
            return (r < 50) ? 1.0 : (r < 75) ? 2.0 : 3.0;
        } else if (fortuneLevel == 3) {
            return (r < 40) ? 1.0 : (r < 60) ? 2.0 : (r > 80) ? 3.0 : 4.0;
        } else {
            return (r < 40) ? (double) fortuneLevel - 3 : (r < 60) ? (double) fortuneLevel - 2 : (r < 80) ? (double) fortuneLevel - 1 : (double) fortuneLevel;
        }
    }

    /**
     * Fully repairs an item if the item is repairable and has missing durability
     *
     * @param item item
     * @return if the item was able to be repaired
     */
    public static boolean repairItem(ItemStack item) {
        return repairItem(item, 100);
    }

    /**
     * Restores a percentage of an items missing durability if the item is both repairable and has missing durability
     * precondition: percent is greater than 0 and less than or equal to 100
     *
     * @param item    item
     * @param percent the % of missing durability to repair
     * @return if the item was able to be repaired
     */
    public static boolean repairItem(ItemStack item, double percent) {
        if (percent > 100 || percent <= 0 || ItemUtils.isNullOrAir(item) || !MaterialConstants.isRepairable(item.getType()) || item.getDurability() == 0) {
            return false;
        }

        item.setDurability((short) (item.getDurability() - (item.getDurability() * (percent / 100))));
        return true;
    }

    /**
     * Sets an items durability to a % of its max if it is repairable
     * precondition: percent is greater than 0 and less than or equal to 100
     *
     * @param item    item
     * @param percent the percentage of the items max durability to set its current durability to
     * @return if the item's durability was successfully updated
     */
    public static boolean setItemDurability(ItemStack item, double percent) {
        if (percent <= 0 || percent > 100 || ItemUtils.isNullOrAir(item) || !MaterialConstants.isRepairable(item.getType())) {
            return false;
        }

        item.setDurability((short) (item.getType().getMaxDurability() * ((100 - percent) / 100)));
        return true;
    }

    /**
     * Returns the entity type associated with a spawn egg
     *
     * @param id id
     * @return entity type or null
     */
    public static EntityType getEntityTypeFromSpawnEggId(int id) {
        switch (id) {
            case 120:
                return EntityType.VILLAGER;
            case 100:
                return EntityType.HORSE;
            case 96:
                return EntityType.MUSHROOM_COW;
            case 67:
                return EntityType.ENDERMITE;
            case 62:
                return EntityType.MAGMA_CUBE;
            case 60:
                return EntityType.SILVERFISH;
            case 91:
                return EntityType.SHEEP;
            case 93:
                return EntityType.CHICKEN;
            case 92:
                return EntityType.COW;
            case 61:
                return EntityType.BLAZE;
            case 59:
                return EntityType.CAVE_SPIDER;
            case 57:
                return EntityType.PIG_ZOMBIE;
            case 51:
                return EntityType.SKELETON;
            case 101:
                return EntityType.RABBIT;
            case 68:
                return EntityType.GUARDIAN;
            case 94:
                return EntityType.SQUID;
            case 95:
                return EntityType.WOLF;
            case 98:
                return EntityType.OCELOT;
            case 66:
                return EntityType.WITCH;
            case 65:
                return EntityType.BAT;
            case 58:
                return EntityType.ENDERMAN;
            case 50:
                return EntityType.CREEPER;
            case 55:
                return EntityType.SLIME;
            case 54:
                return EntityType.ZOMBIE;
            case 90:
                return EntityType.PIG;
            default:
                return null;
        }
    }

    /**
     * Serialize an array of ItemStacks (credit: https://gist.github.com/graywolf336/8153678)
     *
     * @param items items
     * @return serialized items
     * @throws IllegalStateException exception
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Deserialize a serialized ItemStack array (credit: https://gist.github.com/graywolf336/8153678)
     *
     * @param data data
     * @return ItemStack array
     * @throws IOException exception
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            final ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

}
