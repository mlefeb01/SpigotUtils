package com.github.mlefeb01.spigotutils.api.object;

/**
 * Mutable pair
 *
 * @param <K> type k
 * @param <V> type v
 * @author Matt Lefebvre
 */
public class MutablePair<K, V> implements Pair<K, V> {
    private K k;
    private V v;

    /**
     * Constructor
     *
     * @param k k
     * @param v v
     */
    public MutablePair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    @Override
    public K getK() {
        return k;
    }

    @Override
    public void setK(K k) {
        this.k = k;
    }

    @Override
    public V getV() {
        return v;
    }

    @Override
    public void setV(V v) {
        this.v = v;
    }

    @Override
    public MutablePair<K, V> clone() {
        return new MutablePair<>(k, v);
    }

    @Override
    public String toString() {
        return String.format("(K: %s, V: %s)", k, v);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MutablePair)) {
            return false;
        }

        final MutablePair<K, V> mutablePair = (MutablePair<K, V>) object;
        return this.k.equals(mutablePair.k) && this.v.equals(mutablePair.v);
    }

    /**
     * Static factory method
     *
     * @param k k
     * @param v v
     * @return MutablePair
     */
    public static <K, V> MutablePair<K, V> of(K k, V v) {
        return new MutablePair<>(k, v);
    }

}
