package com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem;

/**
 * Different cost scaling options for item upgrade prices
 *
 * @author Matt Lefebvre
 */
public enum CostScaling {
    STATIC,
    LINEAR;

    /**
     * Calculates the cost of purchasing a {@link AbstractItemUpgrade}
     *
     * @param upgradeMeta upgradeMeta
     * @return
     */
    public static long calculateUpgradeCost(UpgradeMeta upgradeMeta, int currentLevel) {
        switch (upgradeMeta.getCostScaling()) {
            case STATIC:
                return upgradeMeta.getCost();
            case LINEAR:
                return (currentLevel + 1) * upgradeMeta.getCost();
            default:
                throw new IllegalArgumentException();
        }
    }

}
