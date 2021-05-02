package com.github.mlefeb01.spigotutils.customitem;

import com.github.mlefeb01.spigotutils.SpigotUtils;
import com.github.mlefeb01.spigotutils.api.utils.ItemUtils;
import com.github.mlefeb01.spigotutils.customitem.eventwrapper.*;
import com.github.mlefeb01.spigotutils.customitem.cooldownitem.AbstractThrowableItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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
        return CustomItemRegistry.getInstance().get(nbtItem.getString(AbstractCustomItem.CUSTOM_ITEM_NBT));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final ItemStack item = event.getPlayer().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        // TODO check canncelled? before or after the wrapper

        final BlockBreakEventWrapper wrapper = new BlockBreakEventWrapper(event, item, nbtItem, new ArrayList<>(event.getBlock().getDrops()));
        customItem.onBlockBreak(wrapper);

        if (wrapper.getDrops().isEmpty()) {
            return;
        }

        // Must cancel the event and break manually to prevent double drops and still achieve block drop list mutability
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);

        final Location location = event.getBlock().getLocation().add(0.5, 1.1, 0.5);
        final World world = location.getWorld();
        wrapper.getDrops().forEach(drop -> world.dropItem(location, drop));
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event) {
        final ItemStack item = event.getItem();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemEnchant(new EnchantItemEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent event) {
        final ItemStack item = event.getEntity().getItemStack();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemMerge(new ItemMergeEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        final ItemStack item = event.getEntity().getItemStack();
        final NBTItem nbtItem = new NBTItem(item);
        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemDespawn(new ItemDespawnEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final ItemStack item = event.getPlayer().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerJoin(new PlayerJoinEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final ItemStack item = event.getPlayer().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerQuit(new PlayerQuitEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final ItemStack item = event.getEntity().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerDeath(new PlayerDeathEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        final ItemStack item = event.getFuel();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onFurnaceBurn(new FurnaceBurnEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onHopperPickup(InventoryPickupItemEvent event) {
        final ItemStack item = event.getItem().getItemStack();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onHopperPickup(new InventoryPickupItemEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        final ItemStack item = event.getItemDrop().getItemStack();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemDrop(new PlayerDropItemEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        final ItemStack item = event.getBrokenItem();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemBreak(new PlayerItemBreakEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        final ItemStack item = event.getItem();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemDamage(new PlayerItemDamageEventWrapper(event, item, nbtItem));
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
            oldCustomItem.onPlayerItemHeld(new PlayerItemHeldEventWrapper(event, oldItem, newItem, oldNbt, newNbt, PlayerItemHeldEventWrapper.SwitchReason.UNEQUIPPING));
        }

        if (newCustomItem != null) {
            newCustomItem.onPlayerItemHeld(new PlayerItemHeldEventWrapper(event, oldItem, newItem, oldNbt, newNbt, PlayerItemHeldEventWrapper.SwitchReason.EQUIPPING));
        }
    }

    @EventHandler
    public void onPlayerItemPickup(PlayerPickupItemEvent event) {
        final ItemStack item = event.getItem().getItemStack();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerItemPickup(new PlayerPickupItemEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerItemShift(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }

        final ItemStack item = event.getPlayer().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerSneak(new PlayerToggleSneakEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getPlayer().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerInteract(new PlayerInteractEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        final ItemStack item = event.getPlayer().getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onPlayerInteractEntity(new PlayerInteractEntityEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onItemDamage(EntityDamageEvent event) {
        final Entity entity = event.getEntity();
        if (!(entity instanceof Item)) {
            return;
        }

        final ItemStack item = ((Item) entity).getItemStack();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onItemDamage(new EntityDamageEventWrapper(event, item, nbtItem));
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

            customItem.onItemCraft(new PrepareItemCraftEventWrapper(event, item, nbtItem));
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

        customItem.onItemRepair(new AnvilEventWrapper(event, cursor, nbtItem));
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        final Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }

        final ItemStack item = player.getItemInHand();
        final NBTItem nbtItem = nbtHelper(item);
        if (nbtItem == null) {
            return;
        }

        final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
        if (customItem == null) {
            return;
        }

        customItem.onEntityKill(new EntityDeathEventWrapper(event, item, nbtItem));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            final ItemStack item = ((Player) event.getDamager()).getItemInHand();
            final NBTItem nbtItem = nbtHelper(item);
            if (nbtItem == null) {
                return;
            }

            final AbstractCustomItem customItem = customItemFromNbt(nbtItem);
            if (customItem == null) {
                return;
            }

            customItem.onEntityDamage(new EntityDamageByEntityEventWrapper(event, nbtItem, item));
        } else if (event.getDamager() instanceof Projectile) {
            final Projectile projectile = (Projectile) event.getDamager();
            if (!projectile.hasMetadata(AbstractThrowableItem.THROWABLE_ITEM_METADATA)) {
                return;
            }

            final AbstractCustomItem customItem = CustomItemRegistry.getInstance().get(projectile.getMetadata(AbstractThrowableItem.THROWABLE_ITEM_METADATA).get(0).asString());
            projectile.removeMetadata(AbstractThrowableItem.THROWABLE_ITEM_METADATA, SpigotUtils.getInstance());
            if (!(customItem instanceof AbstractThrowableItem)) {
                return;
            }

            ((AbstractThrowableItem) customItem).onHit(projectile, event.getEntity());
        }

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();
        if (!projectile.hasMetadata(AbstractThrowableItem.THROWABLE_ITEM_METADATA)) {
            return;
        }

        final AbstractCustomItem customItem = CustomItemRegistry.getInstance().get(projectile.getMetadata(AbstractThrowableItem.THROWABLE_ITEM_METADATA).get(0).asString());
        projectile.removeMetadata(AbstractThrowableItem.THROWABLE_ITEM_METADATA, SpigotUtils.getInstance());
        if (!(customItem instanceof AbstractThrowableItem)) {
            return;
        }

        ((AbstractThrowableItem) customItem).onHit(projectile, null);
    }

}
