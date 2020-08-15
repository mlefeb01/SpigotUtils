package com.github.mlefeb01.spigotutils.api.object;

/**
 * A reward with a weight
 *
 * @param <T> The rewards type
 * @author Matt Lefebvre
 */
public class Reward<T> {
    private final T t;
    private final double weight;

    /**
     * Constructor
     *
     * @param t reward
     * @param weight weight
     */
    public Reward(T t, double weight) {
        this.t = t;
        this.weight = weight;
    }

    /**
     * Getter for the reward
     *
     * @return reward
     */
    public T getT() {
        return t;
    }

    /**
     * Getter for the rewards weight
     *
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

}
