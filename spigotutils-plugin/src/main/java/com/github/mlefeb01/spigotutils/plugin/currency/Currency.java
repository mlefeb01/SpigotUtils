package com.github.mlefeb01.spigotutils.plugin.currency;

import com.github.mlefeb01.spigotutils.plugin.struct.Named;
import org.bukkit.OfflinePlayer;

/**
 * Models a currency and basic operations that will need to be defined on it
 *
 * @author Matt Lefebvre
 */
public interface Currency extends Named {

    /**
     * Checks if the target player has minimum the supplied amount
     *
     * @param player player
     * @param amount amount
     * @return if the player has at least the amount
     */
    boolean has(OfflinePlayer player, long amount);

    /**
     * Removes amount from the player's balance
     *
     * @param player player
     * @param amount amount
     */
    void remove(OfflinePlayer player, long amount);

    /**
     * Resets a player's balance
     *
     * @param player player
     */
    void reset(OfflinePlayer player);

    /**
     * Adds amount to the player's balance
     *
     * @param player player
     * @param amount amount
     */
    void add(OfflinePlayer player, long amount);

    /**
     * Gets the amount of this currency a player has
     *
     * @param player player
     * @return currency
     */
    long get(OfflinePlayer player);

    /**
     * Returns the currencies symbol
     *
     * @return symbol
     */
    String getCurrencySymbol();

}
