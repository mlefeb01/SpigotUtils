package com.github.mlefeb01.spigotutils.customitem.cooldownitem;

import com.github.mlefeb01.spigotutils.api.object.Cooldown;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A {@link AbstractCustomItem} that has a single cooldown associated with it. If your custom item requires multiple cooldowns
 * for the item, or a more sophisticated system you should extend {@link AbstractCustomItem}
 * @author Matt Lefebvre
 */
public abstract class AbstractCooldownItem extends AbstractCustomItem {
    private final Map<UUID, Cooldown> cooldownMap;

    /**
     * Constructor
     * @param name name
     */
    public AbstractCooldownItem(String name) {
        super(name);
        this.cooldownMap = new HashMap<>();
    }

    /**
     * Checks whether the player is on cooldown. If the player is off cooldown, they will be put on cooldown
     * and false will be returned. If the player is on cooldown, they will be sent a message with the remaining cooldown
     * and true is returned. Call this method wherever necessary in your custom item implementation
     * @param player player
     * @return true/false
     */
    public boolean playerOnCooldown(Player player) {
        final int cooldownInSeconds = getCooldownInSeconds();
        if (cooldownInSeconds > 0) {
            final Cooldown cooldown = cooldownMap.get(player.getUniqueId());
            if (cooldown == null || cooldown.isOver()) {
                cooldownMap.put(player.getUniqueId(), new Cooldown(cooldownInSeconds));
                return false;
            } else {
                player.sendMessage(TextUtils.color(getCooldownMessage().replace("%remaining%", String.format("%,.2f", cooldown.calculateRemaining() / 1000.0))));
                return true;
            }
        }
        // The items cooldown < 0, impossible to be on cooldown
        return false;
    }

    /**
     * The cooldown of this item in seconds
     * @return cooldown
     */
    public abstract int getCooldownInSeconds();

    /**
     * The message sent when the player is on cooldown for this item (%remaining% for the time placeholder)
     * @return message
     */
    public abstract String getCooldownMessage();

}
