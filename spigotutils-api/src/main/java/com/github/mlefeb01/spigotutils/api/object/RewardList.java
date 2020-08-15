package com.github.mlefeb01.spigotutils.api.object;

import  java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Stores rewards in a list and can pick a random reward
 *
 * @param <T> type
 * @author Matt Lefebvre
 */
public class RewardList<T> {
    private final LinkedList<Reward<T>> rewards;
    private double totalWeight;

    /**
     * Constructor
     */
    public RewardList() {
        this.rewards = new LinkedList<>();
    }

    /**
     * Adds a reward to the list
     * @param reward reward
     */
    public void add(Reward<T> reward) {
        this.rewards.add(reward);
        this.totalWeight += reward.getWeight();
    }

    /**
     * Clears the reward list
     */
    public void clear() {
        this.rewards.clear();
        this.totalWeight = 0;
    }

    /**
     * Picks a random reward from the list
     * @return null if empty or a random reward
     */
    public Reward<T> getReward() {
        double r = ThreadLocalRandom.current().nextDouble(0, totalWeight);
        for (Reward<T> reward : rewards) {
            r -= reward.getWeight();
            if (r <= 0) {
                return reward;
            }
        }
        return null;
    }

}
