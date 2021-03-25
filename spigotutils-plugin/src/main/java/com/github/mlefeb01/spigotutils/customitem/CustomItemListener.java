package com.github.mlefeb01.spigotutils.customitem;

import com.github.mlefeb01.spigotutils.api.utils.ItemUtils;
import com.github.mlefeb01.spigotutils.customitem.eventwrapper.*;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

/**
 * Entry point for {@link AbstractCustomItem}
 * @author Matt Lefebvre
 */
public final class CustomItemListener implements Listener {

    // Returns null or an NBTItem if the item has the custom item nbt key
    private NBTItem nbtHelper(ItemStack item) {
        if (ItemUtils.isNullOrAir(item)) {
            return null;
        }

        final NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasKey(AbstractCustomItem.CUSTOM_ITEM_NBT)) {
            return null;
        }

        return nbtItem;
    }

    // Returns the custom item associated with the nbt, or null if it does not exist. should be called after nbtHelper
    private AbstractCustomItem customItemFromNbt(NBTItem nbtItem) {
        return CustomItemRegistry.getCustomItem(nbtItem.getString(AbstractCustomItem.CUSTOM_ITEM_NBT));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getPlayer().getItemInHand());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onBlockBreak(new BlockBreakEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getItem());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemEnchant(new EnchantItemEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getEntity().getItemStack());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemMerge(new ItemMergeEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        final NBTItem nbtItem = new NBTItem(event.getEntity().getItemStack());
        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemDespawn(new ItemDespawnEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getPlayer().getItemInHand());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerJoin(new PlayerJoinEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getPlayer().getItemInHand());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerQuit(new PlayerQuitEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getEntity().getItemInHand());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerDeath(new PlayerDeathEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getFuel());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onFurnaceBurn(new FurnaceBurnEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onHopperPickup(InventoryPickupItemEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getItem().getItemStack());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onHopperPickup(new InventoryPickupItemEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getItemDrop().getItemStack());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemDrop(new PlayerDropItemEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getBrokenItem());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemBreak(new PlayerItemBreakEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getItem());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemDamage(new PlayerItemDamageEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        final ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
        final ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        final NBTItem oldNbt = nbtHelper(oldItem);
        final AbstractCustomItem oldCustomItem = oldNbt == null ? null : customItemFromNbt(oldNbt);

        final NBTItem newNbt = nbtHelper(newItem);
        final AbstractCustomItem newCustomItem = newNbt == null ? null : customItemFromNbt(newNbt);

        if (oldCustomItem != null) {
            oldCustomItem.onPlayerItemHeld(new PlayerItemHeldEventWrapper(event, oldNbt, newNbt, PlayerItemHeldEventWrapper.SwitchReason.UNEQUIPPING));
        }

        if (newCustomItem != null) {
            newCustomItem.onPlayerItemHeld(new PlayerItemHeldEventWrapper(event, oldNbt, newNbt, PlayerItemHeldEventWrapper.SwitchReason.EQUIPPING));
        }
    }

    @EventHandler
    public void onPlayerItemPickup(PlayerPickupItemEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getItem().getItemStack());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemPickup(new PlayerPickupItemEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerItemShift(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }

        final NBTItem nbtItem = nbtHelper(event.getPlayer().getItemInHand());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerSneak(new PlayerToggleSneakEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final NBTItem nbtItem = nbtHelper(event.getPlayer().getItemInHand());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        event.setCancelled(true);
        customItem.onPlayerInteract(new PlayerInteractEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onItemDamage(EntityDamageEvent event) {
        final Entity entity = event.getEntity();
        if (!(entity instanceof Item)) {
            return;
        }

        final NBTItem nbtItem = nbtHelper(((Item) entity).getItemStack());
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemDamage(new EntityDamageEventWrapper(event, nbtItem));
    }

    @EventHandler
    public void onCraftingPrepare(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (ItemUtils.isNullOrAir(item)) {
                continue;
            }

            final NBTItem nbtItem = nbtHelper(item);
            if (nbtItem == null) {
                continue;
            }

            final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
            if (customItem == null) {
                continue;
            }

            customItem.onItemCraft(new PrepareItemCraftEventWrapper(event, nbtItem));
        }
    }

    @EventHandler
    public void onAnvilClick(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL) {
            return;
        }

        if (event.getSlotType() == InventoryType.SlotType.RESULT) {
            return;
        }

        final ItemStack cursor = event.getCursor();
        if (ItemUtils.isNullOrAir(cursor)) {
            return;
        }

        final NBTItem nbtItem = nbtHelper(cursor);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemRepair(new AnvilEventWrapper(event, nbtItem));
    }

}
