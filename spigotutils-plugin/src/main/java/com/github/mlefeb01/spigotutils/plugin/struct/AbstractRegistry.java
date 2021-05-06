package com.github.mlefeb01.spigotutils.plugin.struct;

import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class AbstractRegistry<T extends Named> {
    protected final Map<String, T> BY_NAME = new HashMap<>();
    protected final Map<Plugin, List<String>> BY_PLUGIN = new HashMap<>();

    public void register(Plugin plugin, T t) {
        BY_NAME.put(t.getName(), t);
        final List<String> itemIds = BY_PLUGIN.getOrDefault(plugin, new LinkedList<>());
        BY_PLUGIN.putIfAbsent(plugin, itemIds);
        itemIds.add(t.getName());
    }

    public void unregister(Plugin plugin, T t) {
        final List<String> itemIds = BY_PLUGIN.get(plugin);
        if (itemIds == null) {
            return;
        }
        itemIds.removeIf(str -> str.equals(t.getName()));
        BY_NAME.remove(t.getName());
    }

    public void unregisterAll(Plugin plugin) {
        final List<String> itemIds = BY_PLUGIN.remove(plugin);
        if (itemIds == null) {
            return;
        }
        itemIds.forEach(BY_NAME::remove);
    }

    public T get(String name) {
        return BY_NAME.get(name);
    }

    public Collection<T> getAll() {
        return Collections.unmodifiableCollection(BY_NAME.values());
    }

}
