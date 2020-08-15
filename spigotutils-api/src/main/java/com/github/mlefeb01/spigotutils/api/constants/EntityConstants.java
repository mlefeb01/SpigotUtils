package com.github.mlefeb01.spigotutils.api.constants;

import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Entity constants
 *
 * @author Matt Lefebvre
 */
public final class EntityConstants {

    private EntityConstants() {
        throw new AssertionError();
    }

    public static final Set<EntityType> PASSIVE_MOBS = Collections.unmodifiableSet(EnumSet.of(
            EntityType.PIG, EntityType.CHICKEN, EntityType.SHEEP, EntityType.COW, EntityType.MUSHROOM_COW,
            EntityType.RABBIT, EntityType.RABBIT, EntityType.BAT, EntityType.IRON_GOLEM, EntityType.OCELOT,
            EntityType.SNOWMAN, EntityType.HORSE, EntityType.SQUID, EntityType.VILLAGER, EntityType.WOLF
    ));

    public static final Set<EntityType> HOSTILE_MOBS = Collections.unmodifiableSet(EnumSet.of(
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.ENDERMITE,
            EntityType.BLAZE, EntityType.SLIME, EntityType.MAGMA_CUBE, EntityType.WITHER, EntityType.PIG_ZOMBIE,
            EntityType.ENDER_DRAGON, EntityType.SILVERFISH, EntityType.ENDERMAN, EntityType.CREEPER, EntityType.GHAST,
            EntityType.GIANT, EntityType.GUARDIAN, EntityType.WITCH
    ));

    public static boolean isPassive(EntityType type) {
        return PASSIVE_MOBS.contains(type);
    }

    public static boolean isHostile(EntityType type) {
        return HOSTILE_MOBS.contains(type);
    }


}
