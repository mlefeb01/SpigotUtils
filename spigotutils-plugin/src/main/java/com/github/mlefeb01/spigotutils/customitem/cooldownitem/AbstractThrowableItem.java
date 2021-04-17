package com.github.mlefeb01.spigotutils.customitem.cooldownitem;

import com.github.mlefeb01.spigotutils.SpigotUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * An extension of {@link AbstractConsumableItem} that when the item is consumed, will launch a projectile.  This class
 * provides two new methods lifecycle methods - when the projectile is initially launched and when the projectile hits
 * @author Matt Lefebvre
 */
public abstract class AbstractThrowableItem extends AbstractConsumableItem {
    public static final String THROWABLE_ITEM_METADATA = "su-titem";
    private final Class<? extends Projectile> projectileClass;

    /**
     * Constructor
     * @param name name
     * @param projectileClass projectileClass (e.g. - Snowball.class)
     */
    public AbstractThrowableItem(String name, Class<? extends Projectile> projectileClass) {
        super(name);
        this.projectileClass = projectileClass;
    }

    @Override
    public void onItemConsume(Player player) {
        final Projectile projectile = player.launchProjectile(projectileClass);
        projectile.setMetadata(THROWABLE_ITEM_METADATA, new FixedMetadataValue(SpigotUtils.getInstance(), getName()));
        onThrow(player, projectile);
    }

    /**
     * When the projectile is initially launched
     * @param player player
     * @param projectile projectile
     */
    public void onThrow(Player player, Projectile projectile) {}

    /**
     * When the projectile hits an entity or a block (entity being null means no entity is hit)
     * @param projectile the projectile
     * @param entity the entity hit with the projectile (null if no entity is hit)
     */
    public void onHit(Projectile projectile, Entity entity) {}

}
