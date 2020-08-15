package com.github.mlefeb01.spigotutils.api.config;

import com.github.mlefeb01.spigotutils.api.utils.HashUtils;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Model config class for YML configurations
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractConfig {
    /**
     * The plugin this config belongs to
     */
    private final Plugin plugin;
    /**
     * The config object
     */
    protected final YamlConfiguration config;
    /**
     * The name of this config (e.g. - config.yml)
     */
    private final String fileName;
    /**
     * Should this configuration be automatically updated when changes are made to its respective file
     */
    private final boolean autoReload;

    /**
     * Constructor
     *
     * @param plugin The plugin this config belongs to
     * @param fileName the name of the config file (e.g. - config.yml)
     */
    public AbstractConfig(Plugin plugin, String fileName) {
        this(plugin, fileName, false);
    }

    /**
     * Constructor
     *
     * @param plugin the plugin this config belongs to
     * @param fileName the name of this config file (e.g. - config.yml)
     * @param autoreload whether this config should be autoreloaded
     */
    public AbstractConfig(Plugin plugin, String fileName, boolean autoreload) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.autoReload = autoreload;
        this.config = new YamlConfiguration();
    }

    /**
     * Returns whether the the config should be automatically reloaded
     *
     * @return autoreload
     */
    public boolean isAutoReload() {
        return this.autoReload;
    }

    /**
     * Returns the filename of this config (e.g. - config.yml)
     *
     * @return filename
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Returns the plugin associated with this config
     *
     * @return plugin
     */
    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Returns the {@link Path} associated with this config file
     *
     * @return path
     */
    public Path getConfigPath() {
        return Paths.get(this.plugin.getDataFolder().toPath().toString(), this.fileName);
    }

    /**
     * Loads the config and creates necessary directories/files if not present
     */
    public void load() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        final Path path = getConfigPath();
        if (!Files.exists(path)) {
            try {
                Files.copy(this.plugin.getClass().getClassLoader().getResourceAsStream(this.fileName), path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            this.config.load(path.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cache();
    }

    /**
     * Returns a String from the config colored
     * note: use this method when parsing a single String value
     * e.g. -
     * message: "hello"
     *
     * @param path config path
     * @return colored message
     */
    protected String getMessage(String path) {
        return TextUtils.color(config.getString(path));
    }

    /**
     * Returns a String from the config colored
     * note: use this method when parsing a multi lined message (stored in a list, - or [] syntax)
     * e.g. -
     * message:
     *  - line 1
     *  - line 2
     *  - line 3
     *
     *
     * @param path config path
     * @return colored message
     */
    protected String getMultiMessage(String path) {
        return TextUtils.color(String.join("\n", config.getStringList(path)));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AbstractConfig)) {
            return false;
        }

        final AbstractConfig config = (AbstractConfig) object;
        return this.plugin == config.plugin && this.fileName.equals(config.fileName) && this.autoReload == config.autoReload;
    }

    @Override
    public int hashCode() {
        final int result = 17;
        final int c = HashUtils.hash(this.plugin) + HashUtils.hash(this.fileName) + HashUtils.hash(this.autoReload);
        return 31 * result + c;
    }

    @Override
    public String toString() {
        return String.format("plugin: %s, fileName: %s, autoReload: %b", this.plugin, this.fileName, this.autoReload);
    }

    /*
    CONFIG DATA
    */

    /**
     * Caches data from the config
     */
    protected abstract void cache();

    /*
    GETTERS
    */

}
