package com.github.mlefeb01.spigotutils.api.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods for common file operations
 *
 * @author Matt Lefebvre
 */
public final class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * Loads a map from a file
     *
     * @param gson gson
     * @param path path
     * @param typeToken type
     * @param <K> key type
     * @param <V> value type
     * @return map
     */
    public static <K, V> HashMap<K, V> loadMap(Gson gson, Path path, TypeToken<? extends Map<K, V>> typeToken) {
        final HashMap<K, V> map = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            map.putAll(gson.fromJson(reader, typeToken.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Saves a map to a file
     *
     * @param gson gson
     * @param path path
     * @param map map
     * @param <K> key type
     * @param <V> value type
     */
    public static <K, V> void saveMap(Gson gson, Path path, Map<K, V> map) {
        if (map == null) {
            return;
        }

        try (Writer writer = new FileWriter(new File(path.toUri()))) {
            gson.toJson(map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an object from a file
     *
     * @param gson gson
     * @param path path
     * @param type the objects type
     * @param <T> generic return type
     * @return object
     */
    public static <T> T loadObject(Gson gson, Path path, Type type) {
        try {
            return (gson.fromJson(new FileReader(path.toFile()), type));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves an object to a file
     *
     * @param gson gson
     * @param path path
     * @param object object
     */
    public static void saveObject(Gson gson, Path path, Object object) {
        if (object == null) {
            return;
        }

        try (Writer writer = new FileWriter(new File(path.toUri()))) {
            gson.toJson(object, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
