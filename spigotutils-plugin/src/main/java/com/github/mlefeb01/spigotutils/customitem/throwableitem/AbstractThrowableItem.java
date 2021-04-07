package com.github.mlefeb01.spigotutils.customitem.throwableitem;

import com.github.mlefeb01.spigotutils.SpigotUtils;
import com.github.mlefeb01.spigotutils.api.object.Cooldown;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import com.github.mlefeb01.spigotutils.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.customitem.eventwrapper.PlayerInteractEventWrapper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractThrowableItem extends AbstractCustomItem {
    public static final String THROWABLE_ITEM_METADATA = "su-titem";
    private final Map<UUID, Cooldown> cooldownMap;
    private final Class<? extends Projectile> projectileClass;

    public AbstractThrowableItem(String name, Class<? extends Projectile> projectileClass) {
        super(name);
        this.cooldownMap = new HashMap<>();
        this.projectileClass = projectileClass;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEventWrapper wrapper) {
        final Action action = wrapper.getEvent().getAction();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        wrapper.getEvent().setCancelled(true);

        final Player player = wrapper.getEvent().getPlayer();

        // TODO probably throw this in a new class called AbstractCooldownItem and have Throwable extend it
        final int cooldownInSeconds = getCooldownInSeconds();
        if (cooldownInSeconds > 0) {
            final Cooldown cooldown = cooldownMap.get(player.getUniqueId());
            if (cooldown == null || cooldown.isOver()) {
                cooldownMap.put(player.getUniqueId(), new Cooldown(cooldownInSeconds));
            } else {
                player.sendMessage(TextUtils.color(getCooldownMessage().replace("%remaining%", String.format("%,.2f", cooldown.calculateRemaining() / 1000.0))));
                return;
            }
        }

        final ItemStack item = wrapper.getItem();
        if (item.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            item.setAmount(item.getAmount() - 1);
        }

        final Projectile projectile = player.launchProjectile(projectileClass);
        projectile.setMetadata(THROWABLE_ITEM_METADATA, new FixedMetadataValue(SpigotUtils.getInstance(), getName()));

        onThrow(player, projectile);
    }

    public abstract int getCooldownInSeconds();

    public abstract String getCooldownMessage();

    public void onThrow(Player player, Projectile projectile) {}

    public void onHit(ProjectileHitEvent event) {}

}
