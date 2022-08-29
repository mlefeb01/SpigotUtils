package com.github.mlefeb01.spigotutils.api.object;

/**
 * Interface for Pair objects
 *
 * @param <K> type k
 * @param <V> type v
 * @author Matt Lefebvre
 */
public interface Pair<K, V> {

    /**
     * Getter for K
     *
     * @return k
     */
    K getK();

    /**
     * Setter for K
     *
     * @param k k
     */
    void setK(K k);

    /**
     * Getter for V
     *
     * @return v
     */
    V getV();

    /**
     * Setter for V
     *
     * @param v v
     */
    void setV(V v);

    /**
     * Clones the pair instance
     *
     * @return clone of the pair
     */
    Pair<K, V> clone();

}
