package com.github.mlefeb01.spigotutils.api.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.plugin.Plugin;

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
     * @param gson      gson
     * @param path      path
     * @param typeToken type
     * @param <K>       key type
     * @param <V>       value type
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
     * @param map  map
     * @param <K>  key type
     * @param <V>  value type
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
     * @param <T>  generic return type
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
     * @param gson   gson
     * @param path   path
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

    /**
     * Creates a file if the file is not present
     *
     * @param plugin      plugin
     * @param destination destination
     * @param fileName    fileName
     */
    public static void createFile(Plugin plugin, Path destination, String fileName) {
        final Path fileDestination = destination.resolve(fileName);
        if (!Files.exists(fileDestination)) {
            try {
                Files.copy(plugin.getClass().getClassLoader().getResourceAsStream(fileName), fileDestination);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates files if they are not present
     *
     * @param plugin      plugin
     * @param destination destination
     * @param fileNames   fileNames
     */
    public static void createFiles(Plugin plugin, Path destination, String[] fileNames) {
        for (String fileName : fileNames) {
            createFile(plugin, destination, fileName);
        }
    }

    /**
     * Creates a single directory
     *
     * @param path      the path where the directory is located
     * @param directory directory name
     */
    public static void createDirectory(Path path, String directory) {
        try {
            Files.createDirectories(path.resolve(directory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates multiple directories at a specified path
     *
     * @param path        the path where the directories are located
     * @param directories directory names
     */
    public static void createDirectories(Path path, String[] directories) {
        for (String directoryName : directories) {
            createDirectory(path, directoryName);
        }
    }

}
