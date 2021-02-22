package com.github.mlefeb01.spigotutils.api.collection;

import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Map implementation specific to {@link PotionEffectType} keys
 *
 * @param <K> {@link PotionEffectType}
 * @param <V> the type of mapped values
 */
public class PotionEffectTypeMap<K extends PotionEffectType, V> extends AbstractMap<K, V> implements Map<K, V> {
    private final V[] values;
    private byte size;

    /**
     * PotionEffectTypeMap default constructor
     */
    public PotionEffectTypeMap() {
        this.values = (V[]) new Object[23];
        this.size = 0;
    }

    /**
     * PotionEffectTypeMap constructor given a map
     *
     * @param map map
     */
    public PotionEffectTypeMap(Map<K, V> map) {
        this();
        putAll(map);
    }

    /**
     * {@inheritDoc}
     */
    public int size() {
        return this.size;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsKey(Object key) {
        if (!(key instanceof PotionEffectType)) {
            return false;
        }
        return this.values[potionIdToIndex(((PotionEffectType) key).getId())] != null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsValue(Object value) {
        for (V v : this.values) {
            if (value.equals(v)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public V get(Object key) {
        if (!(key instanceof PotionEffectType)) {
            return null;
        }
        return this.values[potionIdToIndex(((PotionEffectType) key).getId())];
    }

    /**
     * {@inheritDoc}
     */
    public V put(K k, V v) {
        final V oldValue = get(k);
        this.values[potionIdToIndex(k.getId())] = v;
        if (oldValue == null) {
            this.size++;
        }
        return oldValue;
    }

    /**
     * {@inheritDoc}
     */
    public V remove(Object key) {
        if (!(key instanceof PotionEffectType)) {
            return null;
        }
        final int index = potionIdToIndex(((PotionEffectType) key).getId());
        final V oldValue = this.values[index];
        this.values[index] = null;
        if (oldValue != null) {
            this.size--;
        }
        return oldValue;
    }

    /**
     * {@inheritDoc}
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        this.size = 0;
        // Could optionally replace the array instead of iterating over the existing one
        Arrays.fill(values, null);
    }

    /**
     * {@inheritDoc}
     */
    public Set<K> keySet() {
        final Set<K> set = new PotionEffectTypeSet<>();
        for (int n = 0; n < values.length; n++) {
            final V v = values[n];
            if (v == null) {
                continue;
            }
            set.add((K) PotionEffectType.getById(indexToPotionId(n)));
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<V> values() {
        return Arrays.stream(values).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> set = new HashSet<>();
        for (int n = 0; n < values.length; n++) {
            final V v = values[n];
            if (v == null) {
                continue;
            }
            final PotionEffectType type = PotionEffectType.getById(indexToPotionId(n));
            set.add(new Map.Entry<K, V>() {

                @Override
                public K getKey() {
                    return (K) type;
                }

                @Override
                public V getValue() {
                    return v;
                }

                @Override
                public V setValue(Object value) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String toString() {
                    return String.format("(K: %s, V: %s)", type.toString(), v.toString());
                }

            });
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (!(o instanceof PotionEffectTypeMap)) {
            return false;
        }
        return this.entrySet().equals(((PotionEffectTypeMap) o).entrySet());
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        final int result = 17;
        final int c = Arrays.hashCode(values) + this.size;
        return 31 * result * c;
    }

    private int potionIdToIndex(int id) {
        return id - 1;
    }

    private int indexToPotionId(int index) {
        return index + 1;
    }

}
