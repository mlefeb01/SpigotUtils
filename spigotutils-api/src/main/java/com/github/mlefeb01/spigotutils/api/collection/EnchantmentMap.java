package com.github.mlefeb01.spigotutils.api.collection;

import org.bukkit.enchantments.Enchantment;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Map implementation specific to {@link Enchantment} keys
 *
 * @param <K> {@link Enchantment}
 * @param <V> the type of mapped values
 */
public class EnchantmentMap<K extends Enchantment, V> extends AbstractMap<K, V> implements Map<K, V> {
    private final V[] values;
    private byte size;

    /**
     * EnchantmentMap default constructor
     */
    public EnchantmentMap() {
        this.values = (V[]) new Object[25];
        this.size = 0;
    }

    /**
     * EnchantmentMap constructor given another map
     * @param map
     */
    public EnchantmentMap(Map<K, V> map) {
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
        if (!(key instanceof Enchantment)) {
            return false;
        }
        return this.values[enchantmentIdToIndex(((Enchantment) key).getId())] != null;
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
        if (!(key instanceof Enchantment)) {
            return null;
        }
        return this.values[enchantmentIdToIndex(((Enchantment) key).getId())];
    }

    /**
     * {@inheritDoc}
     */
    public V put(K k, V v) {
        final V oldValue = get(k);
        this.values[enchantmentIdToIndex(k.getId())] = v;
        if (oldValue == null) {
            this.size++;
        }
        return oldValue;
    }

    /**
     * {@inheritDoc}
     */
    public V remove(Object key) {
        if (!(key instanceof Enchantment)) {
            return null;
        }
        final int index = enchantmentIdToIndex(((Enchantment) key).getId());
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
        final Set<K> set = new EnchantmentSet<>();
        for (int n = 0; n < values.length; n++) {
            final V v = values[n];
            if (v == null) {
                continue;
            }
            set.add((K) Enchantment.getById(indexToEnchantmentId(n)));
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
            final Enchantment enchantment = Enchantment.getById(indexToEnchantmentId(n));
            set.add(new Map.Entry<K, V>() {

                @Override
                public K getKey() {
                    return (K) enchantment;
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
                    return String.format("(K: %s, V: %s)", enchantment.toString(), v.toString());
                }

            });
        }
        return set;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (!(o instanceof EnchantmentMap)) {
            return false;
        }
        return this.entrySet().equals(((EnchantmentMap) o).entrySet());
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        final int result = 17;
        final int c = Arrays.hashCode(values) + this.size;
        return 31 * result * c;
    }

    /**
     * Converts an enchantment id to an array index. This is necessary because the enchantment ids are not linear
     *
     * @param id id
     * @return index
     */
    private int enchantmentIdToIndex(int id) {
        switch (id) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return id;
            case 16:
                return 9;
            case 17:
                return 10;
            case 18:
                return 11;
            case 19:
                return 12;
            case 20:
                return 13;
            case 21:
                return 14;
            case 32:
                return 15;
            case 33:
                return 16;
            case 34:
                return 17;
            case 35:
                return 18;
            case 48:
                return 19;
            case 49:
                return 20;
            case 50:
                return 21;
            case 51:
                return 22;
            case 61:
                return 23;
            case 62:
                return 24;
            default:
                throw new IllegalArgumentException("Unexpected Enchantment Id: " + id);
        }
    }

    /**
     * Converts an idex generated by an enchantment id back to its respective enchantment id
     *
     * @param index index
     * @return enchantment id
     */
    private int indexToEnchantmentId(int index) {
        switch (index) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return index;
            case 9:
                return 16;
            case 10:
                return 17;
            case 11:
                return 18;
            case 12:
                return 19;
            case 13:
                return 20;
            case 14:
                return 21;
            case 15:
                return 32;
            case 16:
                return 33;
            case 17:
                return 34;
            case 18:
                return 35;
            case 19:
                return 48;
            case 20:
                return 49;
            case 21:
                return 50;
            case 22:
                return 51;
            case 23:
                return 61;
            case 24:
                return 62;
            default:
                throw new IllegalArgumentException("Unexpected Bit Index: " + index);
        }
    }

}
