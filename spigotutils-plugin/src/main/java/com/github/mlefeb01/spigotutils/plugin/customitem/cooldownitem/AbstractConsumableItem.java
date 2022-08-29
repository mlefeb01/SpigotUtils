package com.github.mlefeb01.spigotutils.plugin.customitem.cooldownitem;

import com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.plugin.customitem.eventwrapper.PlayerInteractEventWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link AbstractCustomItem} that when activated (playerinteract right click) will consume the item and
 * then execute some logic
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractConsumableItem extends AbstractCooldownItem {

    /**
     * Constructor
     *
     * @param name name
     */
    public AbstractConsumableItem(String name) {
        super(name);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEventWrapper wrapper) {
        final Action action = wrapper.getEvent().getAction();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        // Prevent actually throwing an item, placing a block, etc.
        wrapper.getEvent().setCancelled(true);

        final Player player = wrapper.getEvent().getPlayer();
        if (playerOnCooldown(player)) {
            return;
        }

        final ItemStack item = wrapper.getItem();
        if (item.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            item.setAmount(item.getAmount() - 1);
        }

        onItemConsume(player);
    }

    /**
     * When the item is consumed
     *
     * @param player player
     */
    public abstract void onItemConsume(Player player);

}
