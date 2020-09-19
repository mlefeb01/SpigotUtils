package com.github.mlefeb01.spigotutils.api.builder;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

/**
 * Builder for {@link PotionEffect}
 *
 * @author Matt Lefebvre
 */
public class PotionBuilder {
    /**
     * The type of potion
     */
    private PotionEffectType type;
    /**
     * The duration of the potion in seconds
     * note: 1 second = 20 game ticks, this is why the addSeconds, addMinutes, and addHours multiply the value by 20
     */
    private int duration;
    /**
     * The amplifier (level) of the potion
     * note: the amplifier is always 1 higher than the provided value, so methods for setting the raw and adjusted amplifier exist
     */
    private int amplifier;

    /**
     * Constructor
     */
    public PotionBuilder() {}

    /**
     * Set the potions type
     *
     * @param type potion effect type
     * @return this
     */
    public PotionBuilder setType(PotionEffectType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the raw duration of the potion effect
     *
     * @param duration duration
     */
    public PotionBuilder setRawDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Add seconds to the potions duration
     *
     * @param seconds seconds
     * @return this
     */
    public PotionBuilder addSeconds(int seconds) {
        this.duration += TimeUnit.SECONDS.toSeconds(seconds) * 20;
        return this;
    }

    /**
     * Add minutes to the potions duration
     *
     * @param minutes minutes
     * @return this
     */
    public PotionBuilder addMinutes(int minutes) {
        this.duration += TimeUnit.MINUTES.toSeconds(minutes) * 20;
        return this;
    }

    /**
     * Add hours to the potions duration
     *
     * @param hours hours
     * @return this
     */
    public PotionBuilder addHours(int hours) {
        this.duration += TimeUnit.HOURS.toSeconds(hours) * 20;
        return this;
    }

    /**
     * Set the potions amplifier (raw)
     *
     * @param amplifier amplifier
     * @return this
     */
    public PotionBuilder setRawAmplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    /**
     * Set the potions amplifier (adjusted/actual)
     *
     * @param amplifier amplifier
     * @return this
     */
    public PotionBuilder setAdjustedAmplifier(int amplifier) {
        this.amplifier = --amplifier;
        return this;
    }

    /**
     * Builds the PotionEffect
     *
     * @return PotionEffect
     */
    public PotionEffect build() {
        return new PotionEffect(this.type, this.duration, this.amplifier);
    }

}
