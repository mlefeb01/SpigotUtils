package com.github.mlefeb01.spigotutils.api.collection;

import com.github.mlefeb01.spigotutils.api.object.Weighted;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Collection that wraps a list of Weighted objects. Allows for the selecting of an item randomly from the provided distribution
 * in constant time after an O(n) preprocess
 *
 * @author Matt Lefebvre
 */
public class WeightedList<T extends Weighted> {
    private final List<T> objects;
    private transient final double[] prob;
    private transient final int[] alias;
    private transient boolean isInitialized;

    /**
     * Constructor
     *
     * @param objects weighted distribution
     */
    public WeightedList(List<T> objects) {
        if (objects.isEmpty()) {
            throw new IllegalStateException("list must be nonempty!");
        }
        this.objects = objects;
        this.prob = new double[objects.size()];
        this.alias = new int[objects.size()];
        this.isInitialized = false;
    }


    private void preprocess() {
        final int size = objects.size();

        final Deque<Integer> small = new LinkedList<>();
        final Deque<Integer> large = new LinkedList<>();

        final double totalWeight = objects.stream().mapToDouble(Weighted::getWeight).sum();
        for (int n = 0; n < objects.size(); n++) {
            final T t = objects.get(n);

            final double adjusted = (t.getWeight() / totalWeight) * size;
            prob[n] = adjusted;

            (adjusted < 1 ? small : large).add(n);
        }

        while (!small.isEmpty() && !large.isEmpty()) {
            final int indexSmall = small.poll();
            final int indexLarge = large.poll();

            final double probSmall = prob[indexSmall];
            final double probLarge = prob[indexLarge];

            prob[indexSmall] = probSmall;
            alias[indexSmall] = indexLarge;

            final double remainingLarge = (probLarge + probSmall) - 1;
            prob[indexLarge] = remainingLarge;

            (remainingLarge < 1 ? small : large).add(indexLarge);
        }

        while (!large.isEmpty()) {
            final int index = large.poll();
            prob[index] = 1;
        }

        while (!small.isEmpty()) {
            final int index = small.poll();
            prob[index] = 1;
        }

        this.isInitialized = true;
    }

    /**
     * Selects an item from the weighted distribution
     *
     * @return T item
     */
    public T select() {
        if (!isInitialized) {
            preprocess();
        }
        final int n = ThreadLocalRandom.current().nextInt(objects.size());
        return objects.get(ThreadLocalRandom.current().nextDouble() < prob[n] ? n : alias[n]);
    }

    /**
     * Static factory method
     *
     * @param objects objects
     * @return WeightedList
     */
    public static <T extends Weighted> WeightedList<T> of(List<T> objects) {
        return new WeightedList<>(objects);
    }

}
