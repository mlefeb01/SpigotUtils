package com.github.mlefeb01.spigotutils.api.utils;

import java.util.Collection;

/**
 * A collection of utility methods to easily compute integers for significant fields to be used in hashCode methods based
 * off of the guidelines listed in "Effective Java Second Edition" by Joshua Bloch
 *
 * The suggested implementation for hashCode is as follows...
 * 1. Store some constant nonzero prime value (e.g. - 17) in an int (commonly referenced as "result")
 * 2. For each significant field (i.e. - the field is used in the Objects equals implementation) in the Object do the following
 *  a. Compute an int hash code c for the field f
 *    i. If the field is a boolean, compute (f ? 1 : 0)
 *    ii. If the field is a byte, char, short, or int compute (int) f
 *    iii. If the field is a long, compute (int) (f ^ (f >>> 32))
 *    iv. If the field is a float, compute Float.floatToIntBits(f)
 *    v. If the field is a double, compute Double.toLongBits(f) and repeat step 2.a.iii
 *    vi. If the field is a null object reference return 0
 *    vii. If the field is a nonnull object reference invoke the objects hashCode method
 *    viii. If the field is an array/collection, treat each element as if it were a separate field
 *  b. Combine the hash code created in step 2.a by doing the following
 *    i. result = 31 * result + c;
 * 3. Return the result
 *
 * Tip: In step 2.a when dealing with fields that are of the types byte, short, char, and integer you can simply add the
 * field to the int c to avoid creating an activation record. The methods are only implemented in this class for sake
 * of consistency. This note does NOT apply to the types array implementations
 *
 * Note: In the array implementations of hash, iteration is preferred over calling the types respective single value hash
 * method to prevent a potentially large amount of activation records from being created. Any potential overhead is intentionally
 * avoided because computing hash codes is meant to be a fast operation. Degrading the performance of hashCode could cause
 * unwanted side effects such as slower performance from data structures that rely on objects hash codes like {@link java.util.HashMap}
 * and {@link java.util.HashSet}
 *
 * @author Matt Lefebvre
 */
public final class HashUtils {

    private HashUtils() {
        throw new AssertionError();
    }

    /**
     * Computes a hash code for a boolean
     * @param f boolean
     * @return hash code
     */
    public static int hash(boolean f) {
        return f ? 1 : 0;
    }

    /**
     * Computes a hash code for an array of booleans
     * @param f boolean array
     * @return hash code
     */
    public static int hash(boolean[] f) {
        int n = 0;
        for (boolean val : f) {
            n += val ? 1 : 0;
        }
        return n;
    }

    /**
     * Computes a hash code for a byte
     * @param f byte
     * @return hash code
     */
    public static int hash(byte f) {
        return f;
    }

    /**
     * Computes a hash code for an array of bytes
     * @param f byte array
     * @return hash code
     */
    public static int hash(byte[] f) {
        int n = 0;
        for (byte val : f) {
            n += val;
        }
        return n;
    }

    /**
     * Computes a hash code for a char
     * @param f char
     * @return hash code
     */
    public static int hash(char f) {
        return f;
    }

    /**
     * Computes a hash code for an array of chars
     * @param f char array
     * @return hash code
     */
    public static int hash(char[] f) {
        int n = 0;
        for (char val : f) {
            n += val;
        }
        return n;
    }

    /**
     * Computes a hash code for a short
     * @param f short
     * @return hash code
     */
    public static int hash(short f) {
        return f;
    }

    /**
     * Computes a hash code for an array of shorts
     * @param f short array
     * @return hash code
     */
    public static int hash(short[] f) {
        int n = 0;
        for (short val : f) {
            n += val;
        }
        return n;
    }

    /**
     * Computes a hash code for an int
     * @param f int
     * @return hash code
     */
    public static int hash(int f) {
        return f;
    }

    /**
     * Computes a hash code for an array of ints
     * @param f int array
     * @return hash code
     */
    public static int hash(int[] f) {
        int n = 0;
        for (int val : f) {
            n += val;
        }
        return n;
    }

    /**
     * Computes a hash code for a long
     * @param f long
     * @return hash code
     */
    public static int hash(long f) {
        return (int) (f ^ (f >>> 32));
    }

    /**
     * Computes a hash code for an array of longs
     * @param f long array
     * @return hash code
     */
    public static int hash(long[] f) {
        int n = 0;
        for (long val : f) {
            n += val ^ (val >>> 32);
        }
        return n;
    }

    /**
     * Computes a hash code for a float
     * @param f float
     * @return hash code
     */
    public static int hash(float f) {
        return Float.floatToIntBits(f);
    }

    /**
     * Computes a hash code for an array of floats
     * @param f float array
     * @return hash code
     */
    public static int hash(float[] f) {
        int n = 0;
        for (float val : f) {
            n += Float.floatToIntBits(val);
        }
        return n;
    }

    /**
     * Computes a hash code for a double
     * @param f double
     * @return hash code
     */
    public static int hash(double f) {
        final long l = Double.doubleToLongBits(f);
        return (int) (l ^ (l >>> 32));
    }

    /**
     * Computes a hash code for an array of doubles
     * @param f double array
     * @return hash code
     */
    public static int hash(double[] f) {
        int n = 0;
        for (double val : f) {
            final long l = Double.doubleToLongBits(val);
            n += l ^ (l >>> 32);
        }
        return n;
    }

    /**
     * Computes a hash code for an object
     * @param f object
     * @return hash code
     */
    public static int hash(Object f) {
        return f == null ? 0 : f.hashCode();
    }

    /**
     * Computes a hash code for an array of objects
     * @param f object array
     * @return hash code
     */
    public static int hash(Object[] f) {
        int n = 0;
        for (Object val : f) {
            n += val == null ? 0 : val.hashCode();
        }
        return n;
    }

    /**
     * Computes a hash code for a collection
     * @param f collection
     * @return hash code
     */
    public static <T> int hash(Collection<T> f) {
        int count = 0;
        for (T val : f) {
            count += val == null ? 0 : val.hashCode();
        }
        return count;
    }

}
