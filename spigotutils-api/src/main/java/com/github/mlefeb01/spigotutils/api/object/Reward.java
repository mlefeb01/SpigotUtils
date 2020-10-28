package com.github.mlefeb01.spigotutils.api.object;

/**
 * A reward with a weight
 *
 * @param <T> The rewards type
 * @author Matt Lefebvre
 */
public class Reward<T> {
    private final T data;
    private final double weight;

    /**
     * Constructor
     *
     * @param data reward
     * @param weight weight
     */
    public Reward(T data, double weight) {
        this.data = data;
        this.weight = weight;
    }

    /**
     * Getter for the reward
     *
     * @return reward
     */
    public T getData() {
        return data;
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
