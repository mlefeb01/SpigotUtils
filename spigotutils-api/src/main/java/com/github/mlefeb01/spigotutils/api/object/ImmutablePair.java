package com.github.mlefeb01.spigotutils.api.object;

/**
 * Immutable Pair, does not support setting k and v after instantiation
 *
 * @param <K> type k
 * @param <V> type v
 * @author Matt Lefebvre
 */
public class ImmutablePair<K, V> implements Pair<K, V> {
    private final K k;
    private final V v;

    /**
     * Constructor
     *
     * @param k k
     * @param v v
     */
    public ImmutablePair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    @Override
    public K getK() {
        return k;
    }

    @Override
    public void setK(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V getV() {
        return v;
    }

    @Override
    public void setV(V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutablePair<K, V> clone() {
        return new ImmutablePair<>(k, v);
    }

    @Override
    public String toString() {
        return String.format("(K: %s, V: %s)", k, v);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ImmutablePair)) {
            return false;
        }

        final ImmutablePair<K, V> immutablePair = (ImmutablePair<K, V>) object;
        return this.k.equals(immutablePair.k) && this.v.equals(immutablePair.v);
    }

    /**
     * Static factory method
     *
     * @param k k
     * @param v v
     * @return ImmutablePair
     */
    public static <K, V> ImmutablePair<K, V> of(K k, V v) {
        return new ImmutablePair<>(k, v);
    }

}
