package com.github.mlefeb01.spigotutils.api.object;

/**
 * Easily add cooldowns to anything
 *
 * @author Matt Lefebvre
 */
public class Cooldown {
    private final long offCooldown;

    /**
     * Cooldown constructor
     *
     * @param seconds int seconds
     */
    public Cooldown(int seconds) {
        this.offCooldown = System.currentTimeMillis() + (seconds * 1000);
    }

    /**
     * Cooldown constructor
     *
     * @param offCooldown when the cooldown ends
     */
    public Cooldown(long offCooldown) {
        this.offCooldown = offCooldown;
    }

    /**
     * Method to check if the cooldown is active
     * @return boolean is the cooldown active
     */
    public boolean isActive() {
        return calculateRemaining() >= 0;
    }

    /**
     * Method to check if the cooldown is over
     * @return boolean is the cooldown over
     */
    public boolean isOver() {
        return calculateRemaining() <= 0;
    }

    /**
     * Method to get the long time that the cooldown is over
     * @return
     */
    public long getOffCooldown() {
        return offCooldown;
    }

    /**
     * Calculates the time remaining on the cooldown
     * @return
     */
    public long calculateRemaining() {
        return offCooldown - System.currentTimeMillis();
    }

}
