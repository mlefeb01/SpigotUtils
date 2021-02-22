package com.github.mlefeb01.spigotutils.api.collection;

import org.bukkit.potion.PotionEffectType;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Lightweight Set implementation specific to {@link PotionEffectType}
 *
 * @param <E> {@link PotionEffectType}
 */
public class PotionEffectTypeSet<E extends PotionEffectType> extends AbstractSet<E> implements Set<E> {
    private int mask;
    private byte size;

    /**
     * PotionEffectTypeSet default constructor
     */
    public PotionEffectTypeSet() {
        this.mask = 0;
        this.size = 0;
    }

    /**
     * PotionEffectTypeSet constructor from another {@link Collection}
     *
     * @param c collection
     */
    public PotionEffectTypeSet(Collection<? extends E> c) {
        this();
        addAll(c);
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
    public boolean contains(Object o) {
        if (!(o instanceof PotionEffectType)) {
            return false;
        } else {
            return getBit(((PotionEffectType) o).getId()) == 1;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int bit = -1;

            @Override
            public boolean hasNext() {
                for (int n = bit + 1; n < 23; n++) {
                    final int bit = (mask >> n) & 1;
                    if (bit == 0) {
                        continue;
                    }
                    this.bit = n;
                    return true;
                }
                return false;
            }

            @Override
            public E next() {
                return (E) PotionEffectType.getById(bit);
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    public Object[] toArray() {
        final Object[] arr = new Object[size];
        int index = 0;
        for (int n = 0; n < 23; n++) {
            final int bit = (mask >> n) & 1;
            if (bit == 0) {
                continue;
            }
            arr[index] = PotionEffectType.getById(n);
            index++;
        }
        return arr;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T[] toArray(T[] a) {
        final T[] arr = a.length == this.size ? a : ((T[]) new Object[this.size]);
        int index = 0;
        for (int n = 0; n < 23; n++) {
            final int bit = (mask >> n) & 1;
            if (bit == 0) {
                continue;
            }
            arr[index] = (T) PotionEffectType.getById(n);
            index++;
        }
        return arr;
    }


    // Modification Operations

    /**
     * {@inheritDoc}
     */
    public boolean add(E e) {
        if (e == null) {
            return false;
        }
        final int bit = getBit(e.getId());
        if (bit == 1) {
            // If the Set already contains this PotionEffectType, return false
            return false;
        }
        flipBit(e.getId());
        this.size++;
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public boolean remove(Object o) {
        if (!(o instanceof PotionEffectType)) {
            return false;
        }
        final int id = ((PotionEffectType) o).getId();
        final int bit = getBit(id);
        if (bit == 0) {
            return false;
        }
        flipBit(id);
        size--;
        return true;
    }


    // Bulk Operations

    /**
     * {@inheritDoc}
     */
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean setChanged = false;
        for (E e : c) {
            final boolean result = add(e);
            if (result && !setChanged) {
                setChanged = true;
            }
        }
        return setChanged;
    }

    /**
     * {@inheritDoc}
     */
    public boolean retainAll(Collection<?> c) {
        boolean setChanged = false;
        for (int n = 0; n < 23; n++) {
            final int bit = (mask >> n) & 1;
            if (bit == 0) {
                continue;
            }
            final PotionEffectType type = PotionEffectType.getById(bit);
            if (c.contains(type)) {
                continue;
            }
            remove(type);
            if (!setChanged) {
                setChanged = true;
            }
        }
        return setChanged;
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeAll(Collection<?> c) {
        boolean setChanged = false;
        for (Object object : c) {
            final boolean result = remove(object);
            if (result && !setChanged) {
                setChanged = true;
            }
        }
        return setChanged;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        this.mask = 0;
        this.size = 0;
    }

    // Comparison and hashing

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (!(o instanceof PotionEffectTypeSet)) {
            return false;
        }
        final PotionEffectTypeSet set = (PotionEffectTypeSet) o;
        return this.mask == set.mask && this.size == set.size;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        final int result = 17;
        final int c = this.mask + this.size;
        return 31 * result * c;
    }

    /**
     * Static factory method to create a new PotionEffectTypeSet instance
     *
     * @param collection collection
     * @param <E>        {@link PotionEffectType}
     * @return PotionEffectTypeSet
     */
    public static <E extends PotionEffectType> PotionEffectTypeSet<E> of(Collection<E> collection) {
        return new PotionEffectTypeSet<>(collection);
    }

    // Bit shifting

    /**
     * Gets the value of the nth bit in this sets mask
     *
     * @param bit nth bit
     * @return 0 or 1
     */
    private int getBit(int bit) {
        // https://stackoverflow.com/questions/9354860/how-to-get-the-value-of-a-bit-at-a-certain-position-from-a-byte
        return ((this.mask >> (bit - 1)) & 1);
    }

    /**
     * Flips the value of the nth bit in this sets mask
     *
     * @param bit nth bit
     */
    private void flipBit(int bit) {
        // https://www.geeksforgeeks.org/toggling-k-th-bit-number/
        this.mask = (this.mask ^ (1 << (bit - 1)));
    }

}

