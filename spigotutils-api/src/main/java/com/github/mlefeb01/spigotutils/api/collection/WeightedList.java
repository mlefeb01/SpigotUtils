package com.github.mlefeb01.spigotutils.api.collection;

import com.github.mlefeb01.spigotutils.api.object.MutablePair;
import com.github.mlefeb01.spigotutils.api.object.Weighted;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Simple collection that supports selecting random items from a weighted distribution in constant time after an O(n^2) preprocess
 * based off of the vose alias method
 *
 * @author Matt Lefebvre
 */
public class WeightedList<T extends Weighted> {
    private final List<T> objects;
    private transient MutablePair<T, Double>[] bucketOne;
    private transient MutablePair<T, Double>[] bucketTwo;
    private transient double bucketHeight;
    private transient boolean isPreprocessed;

    /**
     * Constructor
     * @param objects weighted distribution
     */
    public WeightedList(List<T> objects) {
        if (objects.isEmpty()) {
            throw new IllegalStateException("Objects must be non-empty!");
        }
        this.objects = objects;
        this.bucketOne = null;
        this.bucketTwo = null;
        this.bucketHeight = 0;
        this.isPreprocessed = false;
    }

    /**
     * Divide the input into n (input.length) evenly weighted buckets so when a random selection is made, it is as
     * simple as picking a random bucket and then doing a single comparison between the two entries in that bucket
     */
    private void preprocess() {
        final int n = objects.size();

        /*
        Calculate the sum of weights in the collection and divide by size of the collection to find our bucket height
        note: It is assumed that are chances are non-zero and non-negative
         */
        bucketHeight = objects.stream().mapToDouble(Weighted::getWeight).sum() / n;

        // Create buckets
        bucketOne = new MutablePair[n];
        bucketTwo = new MutablePair[n];

        /*
        Divide the raw input into two work stacks
        Priority Pair - A pair with a weight that is less than or equal to the height the bucket, these will be processed first
        Remaining Pair - A pair with a weight that is greater than the height of the bucket, slices of this pair will fill the remaining second bucket halves
         */
        final Deque<MutablePair<T, Double>> priorityPairs = new ArrayDeque<>();
        final Deque<MutablePair<T, Double>> remainingPairs = new ArrayDeque<>();
        objects.forEach(weighted -> (weighted.getWeight() <= bucketHeight ? priorityPairs : remainingPairs).add(new MutablePair<>(weighted, weighted.getWeight())));

        // Fill the first bucket with priority pairs (pairs with weights <= bucketHeight) or slices of remainingPairs if no priorityPairs remain
        for (int currentBucket = 0; currentBucket < n; currentBucket++) {
            // Attempt to pull a priority pair but if none remain use a remaining pair
            MutablePair<T, Double> pair;
            if (!priorityPairs.isEmpty()) {
                pair = priorityPairs.pop();
            } else if (!remainingPairs.isEmpty()) {
                final MutablePair<T, Double> highPair = remainingPairs.pop();

                // If the remaining pair has a greater height than what we need to fill, add the bigger slice of it back to the remainingPairs stack
                final double remaining = highPair.getV() - bucketHeight;
                if (remaining > 0) {
                    (remaining > bucketHeight ? remainingPairs : priorityPairs).add(new MutablePair<>(highPair.getK(), remaining));
                }

                pair = new MutablePair<>(highPair.getK(), Math.min(bucketHeight, highPair.getV()));
            } else {
                break;
            }

            bucketOne[currentBucket] = pair;
        }

        // At this point the entire first bucket is populated, so fill the second halves of the buckets who do not have a height equal the standard bucket height
        for (int currentBucket = 0; currentBucket < n && !remainingPairs.isEmpty(); currentBucket++) {
            final double heightToFill = bucketHeight - bucketOne[currentBucket].getV();
            if (heightToFill == 0) {
                continue;
            }

            final MutablePair<T, Double> high = remainingPairs.pop();
            final double remaining = high.getV() - heightToFill;
            if (remaining > 0) {
                remainingPairs.add(new MutablePair<>(high.getK(), remaining));
            }

            bucketTwo[currentBucket] = new MutablePair<>(high.getK(), heightToFill);
        }

        isPreprocessed = true;
    }

    /**
     * Selects an item from the weighted distribution
     * @return T item
     */
    public T select() {
        if (!isPreprocessed) {
            preprocess();
        }
        final int bucket = ThreadLocalRandom.current().nextInt(objects.size());
        final MutablePair<T, Double> one = bucketOne[bucket];
        return ThreadLocalRandom.current().nextDouble(bucketHeight) <= one.getV() ? one.getK() : bucketTwo[bucket].getK();
    }

    /**
     * Static factory method
     * @param objects objects
     * @return WeightedList
     */
    public static <T extends Weighted> WeightedList<T> of(List<T> objects) {
        return new WeightedList<>(objects);
    }

}
