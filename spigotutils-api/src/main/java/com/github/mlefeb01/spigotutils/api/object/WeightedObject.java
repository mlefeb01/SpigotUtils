package com.github.mlefeb01.spigotutils.api.object;

/**
 * Wrapper object that holds a generic object and a weight
 *
 * @param <T> type T
 * @author Matt Lefebvre
 */
public class WeightedObject<T> implements Weighted {
    private final T data;
    private final double weight;

    /**
     * Constructor
     *
     * @param data   object
     * @param weight weight
     */
    public WeightedObject(T data, double weight) {
        this.data = data;
        this.weight = weight;
    }

    /**
     * Gets the object this WeightedObject wraps
     *
     * @return object
     */
    public T getData() {
        return data;
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
