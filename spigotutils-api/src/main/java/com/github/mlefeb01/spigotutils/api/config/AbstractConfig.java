package com.github.mlefeb01.spigotutils.api.config;

import com.github.mlefeb01.spigotutils.api.builder.ItemBuilder;
import com.github.mlefeb01.spigotutils.api.builder.PotionBuilder;
import com.github.mlefeb01.spigotutils.api.collection.EnchantmentSet;
import com.github.mlefeb01.spigotutils.api.collection.PotionEffectTypeSet;
import com.github.mlefeb01.spigotutils.api.command.CommandFormat;
import com.github.mlefeb01.spigotutils.api.object.HourMinute;
import com.github.mlefeb01.spigotutils.api.object.Schedule;
import com.github.mlefeb01.spigotutils.api.utils.HashUtils;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Caches data from the config
     */
    protected abstract void cache();

    /**
     * Parses an {@link ItemBuilder} from a YML config in the following format
     *
     * path:
     *  material: string # required
     *  item-id: integer # optional
     *  name: string # optional
     *  lore: list/array # optional
     *  glow: boolean # optional
     *  unbreakable: boolean # optional
     *  enchants: list/array # optional (format "enchant:level")
     *  flags: list/array # optional
     *
     * @param path path
     * @return itemBuilder
     */
    protected ItemBuilder parseItemBuilder(String path) {
        final ConfigurationSection section = config.getConfigurationSection(path);

        final ItemBuilder builder = new ItemBuilder(Material.getMaterial(section.getString("material")));

        // durability
        final int itemId = section.getInt("item-id", Integer.MIN_VALUE);
        if (itemId != Integer.MIN_VALUE) {
            builder.durability((short) itemId);
        }

        // name
        final String name = section.getString("name");
        if (name != null) {
            builder.name(TextUtils.color(name));
        }

        // lore
        final List<String> lore = section.getStringList("lore");
        if (lore != null && !lore.isEmpty()) {
            builder.lore(TextUtils.colorList(lore));
        }

        // glow
        builder.glow(section.getBoolean("glow"));

        // unbreakable
        builder.unbreakable(section.getBoolean("unbreakable"));

        // enchantments
        final List<String> enchants = section.getStringList("enchants");
        if (enchants != null && !enchants.isEmpty()) {
            for (String enchant : enchants) {
                final String[] args = enchant.split(":");
                builder.enchantment(Enchantment.getByName(args[0]), Integer.parseInt(args[1]), false);
            }
        }

        // itemflags
        final List<String> itemFlags = section.getStringList("flags");
        if (itemFlags != null && !itemFlags.isEmpty()) {
            final List<ItemFlag> flags = new ArrayList<>();
            itemFlags.stream().map(ItemFlag::valueOf).forEach(flags::add);
            builder.addItemFlags(flags.toArray(new ItemFlag[]{}));
        }

        return builder;
    }

    /**
     * Parses a {@link PotionBuilder} from a YML config in the following format
     *
     * path:
     *  type: string # required
     *  seconds: integer # required
     *  amplifier: integer # required
     *
     * @param path path
     * @return potionBuilder
     */
    protected PotionBuilder parsePotionBuilder(String path) {
        final ConfigurationSection section = config.getConfigurationSection(path);
        return new PotionBuilder().setType(PotionEffectType.getByName(section.getString("type"))).addSeconds(section.getInt("seconds")).setAdjustedAmplifier(section.getInt("amplifier"));
    }

    /**
     * Parses an {@link EnchantmentSet} from a YML config
     * @param path path (should lead to a list/array of strings)
     * @return set
     */
    protected EnchantmentSet<Enchantment> parseEnchantmentSet(String path) {
        return config.getStringList(path).stream().map(Enchantment::getByName).collect(Collectors.toCollection(EnchantmentSet::new));
    }

    /**
     * Parses a {@link PotionEffectTypeSet} from a YML config
     * @param path path (should lead to a list/array of strings)
     * @return set
     */
    protected PotionEffectTypeSet<PotionEffectType> parsePotionEffectTypeSet(String path) {
        return config.getStringList(path).stream().map(PotionEffectType::getByName).collect(Collectors.toCollection(PotionEffectTypeSet::new));
    }

    /**
     * Parses a {@link HourMinute} from a YML confing in the format "hh:mm"
     * @param path path
     * @return hourMinute
     */
    protected HourMinute parseHourMinute(String path) {
        final String[] args = config.getString(path).split(":");
        return HourMinute.of((byte) Integer.parseInt(args[0]), (byte) Integer.parseInt(args[1]));
    }

    /***
     * Parses a {@link Schedule} from a YML config in the following format
     *
     * path:
     *  - "hh:mm"
     *  - "hh:mm"
     *  - ...
     *
     * @param path path
     * @return schedule
     */
    protected Schedule parseSchedule(String path) {
        return new Schedule(config.getStringList(path).stream().map(hourMinute -> {
            final String[] args = hourMinute.split(":");
            return HourMinute.of((byte) Integer.parseInt(args[0]), (byte) Integer.parseInt(args[1]));
        }).collect(Collectors.toList()));
    }

    /**
     * Parses a {@link CommandFormat} from a YML config in the following format
     *
     * path:
     *  header-left: string
     *  header-right: string
     *  title: string
     *  commandPrefix: string
     *  separator: string
     *  descriptionPrefix: string
     *  footer: string
     *
     * @param path path
     * @return commandFormat
     */
    protected CommandFormat parseCommandFormat(String path) {
        final ConfigurationSection section = config.getConfigurationSection(path);
        return new CommandFormat(
                TextUtils.color(section.getString("header-left")),
                TextUtils.color(section.getString("header-right")),
                TextUtils.color(section.getString("title")),
                TextUtils.color(section.getString("commandPrefix")),
                TextUtils.color(section.getString("separator")),
                TextUtils.color(section.getString("descriptionPrefix")),
                TextUtils.color(section.getString("footer"))
        );
    }

}
