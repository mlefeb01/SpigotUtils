package com.github.mlefeb01.spigotutils.api.collection;

import org.bukkit.enchantments.Enchantment;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Lightweight Set implementation specific to {@link Enchantment}
 *
 * @param <E> {@link Enchantment}
 */
public class EnchantmentSet<E extends Enchantment> extends AbstractSet<E> implements Set<E> {
    private int mask;
    private byte size;

    /**
     * PotionEffectTypeSet default constructor
     */
    public EnchantmentSet() {
        this.mask = 0;
        this.size = 0;
    }

    /**
     * PotionEffectTypeSet constructor from another {@link Collection}
     *
     * @param c collection
     */
    public EnchantmentSet(Collection<? extends E> c) {
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
        if (!(o instanceof Enchantment)) {
            return false;
        } else {
            return getBit(enchantmentIdToBitIndex(((Enchantment) o).getId())) == 1;
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
                for (int n = bit + 1; n < 25; n++) {
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
                return (E) Enchantment.getById(bitIndexToEnchantmentId(bit));
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    public Object[] toArray() {
        final Object[] arr = new Object[size];
        int index = 0;
        for (int n = 0; n < 25; n++) {
            final int bit = (mask >> n) & 1;
            if (bit == 0) {
                continue;
            }
            arr[index] = Enchantment.getById(bitIndexToEnchantmentId(n));
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
        for (int n = 0; n < 25; n++) {
            final int bit = (mask >> n) & 1;
            if (bit == 0) {
                continue;
            }
            arr[index] = (T) Enchantment.getById(bitIndexToEnchantmentId(n));
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
        final int bitIndex = enchantmentIdToBitIndex(e.getId());
        final int bit = getBit(bitIndex);
        if (bit == 1) {
            // If the Set already contains this Enchantment, return false
            return false;
        }
        flipBit(bitIndex);
        this.size++;
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public boolean remove(Object o) {
        if (!(o instanceof Enchantment)) {
            return false;
        }
        final int bitIndex = enchantmentIdToBitIndex(((Enchantment) o).getId());
        if (getBit(bitIndex) == 0) {
            return false;
        }
        flipBit(bitIndex);
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
        for (int n = 0; n < 25; n++) {
            final int bit = (mask >> n) & 1;
            if (bit == 0) {
                continue;
            }
            final Enchantment type = Enchantment.getById(bitIndexToEnchantmentId(bit));
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
        if (!(o instanceof Enchantment)) {
            return false;
        }
        final EnchantmentSet set = (EnchantmentSet) o;
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
     * Static factory method to create a new EnchantmentSet instance
     *
     * @param collection collection
     * @param <E>        {@link Enchantment}
     * @return EnchantmentSet
     */
    public static <E extends Enchantment> EnchantmentSet<E> of(Collection<E> collection) {
        return new EnchantmentSet<>(collection);
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
        return ((this.mask >> (bit)) & 1);
    }

    /**
     * Flips the value of the nth bit in this sets mask
     *
     * @param bit nth bit
     */
    private void flipBit(int bit) {
        // https://www.geeksforgeeks.org/toggling-k-th-bit-number/
        this.mask = (this.mask ^ (1 << (bit)));
    }

    // Id/index conversion

    /**
     * Converts a {@link Enchantment} id to its corresponding bit index. This is necessary because the Enchantment ids
     * are not linear
     *
     * @param id enchantment id
     * @return bit index
     */
    private int enchantmentIdToBitIndex(int id) {
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
     * Converts a bit index to an enchantment id
     *
     * @param bitIndex bit index
     * @return enchantment id
     */
    private int bitIndexToEnchantmentId(int bitIndex) {
        switch (bitIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return bitIndex;
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
                throw new IllegalArgumentException("Unexpected Bit Index: " + bitIndex);
        }
    }

}