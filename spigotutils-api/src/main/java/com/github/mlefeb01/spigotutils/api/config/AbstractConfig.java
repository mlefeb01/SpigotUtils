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
import org.bukkit.inventory.ItemStack;
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
    @Deprecated
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
    @Deprecated
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
     * Gets a configuration section at a specified path
     * @param path path
     * @return section
     */
    protected ConfigurationSection getSection(String path) {
        return config.getConfigurationSection(path);
    }

    /**
     * Gets a boolean from an absolute path
     * @param path path
     * @return boolean
     */
    protected boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    /**
     * Gets a boolean relative to a provided section
     * @param section section
     * @param path path
     * @return boolean
     */
    protected boolean getBoolean(ConfigurationSection section, String path) {
        return section.getBoolean(path);
    }

    /**
     * Gets a boolean from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return boolean
     */
    protected boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    /**
     * Gets a boolean relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return boolean
     */
    protected boolean getBoolean(ConfigurationSection section, String path, boolean def) {
        return section.getBoolean(path, def);
    }

    /**
     * Gets a boolean list from an absolute path
     * @param path path
     * @return boolean list
     */
    protected List<Boolean> getBooleanList(String path) {
        return config.getBooleanList(path);
    }

    /**
     * Gets a boolean list relative to a provided section
     * @param section section
     * @param path path
     * @return boolean list
     */
    protected List<Boolean> getBooleanList(ConfigurationSection section, String path) {
        return section.getBooleanList(path);
    }

    /**
     * Gets a byte list from an absolute path
     * @param path path
     * @return byte list
     */
    protected List<Byte> getByteList(String path) {
        return config.getByteList(path);
    }

    /**
     * Gets a byte list relative to a provided section
     * @param section section
     * @param path path
     * @return byte list
     */
    protected List<Byte> getByteList(ConfigurationSection section, String path) {
        return section.getByteList(path);
    }

    /**
     * Gets a character list from an absolute path
     * @param path path
     * @return character list
     */
    protected List<Character> getCharacterList(String path) {
        return config.getCharacterList(path);
    }

    /**
     * Gets a character list relative to a provided section
     * @param section section
     * @param path path
     * @return character list
     */
    protected List<Character> getCharacterList(ConfigurationSection section, String path) {
        return section.getCharacterList(path);
    }

    /**
     * Gets a double from an absolute path
     * @param path path
     * @return double
     */
    protected double getDouble(String path) {
        return config.getDouble(path);
    }

    /**
     * Gets a double relative to a provided section
     * @param section section
     * @param path path
     * @return double
     */
    protected double getDouble(ConfigurationSection section, String path) {
        return section.getDouble(path);
    }

    /**
     * Gets a double from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return double
     */
    protected double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    /**
     * Gets a double relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return double
     */
    protected double getDouble(ConfigurationSection section, String path, double def) {
        return section.getDouble(path, def);
    }

    /**
     * Gets a double list from an absolute path
     * @param path path
     * @return double list
     */
    protected List<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    /**
     * Gets a double list relative to a provided section
     * @param section section
     * @param path path
     * @return double list
     */
    protected List<Double> getDoubleList(ConfigurationSection section, String path) {
        return section.getDoubleList(path);
    }

    /**
     * Gets a float list from an absolute path
     * @param path path
     * @return float list
     */
    protected List<Float> getFloatList(String path) {
        return config.getFloatList(path);
    }

    /**
     * Gets a float list relative to a provided section
     * @param section section
     * @param path path
     * @return float list
     */
    protected List<Float> getFloatList(ConfigurationSection section, String path) {
        return section.getFloatList(path);
    }

    /**
     * Gets an int from an absolute path
     * @param path path
     * @return int
     */
    protected int getInt(String path) {
        return config.getInt(path);
    }

    /**
     * Gets an int relative to a provided section
     * @param section section
     * @param path path
     * @return int
     */
    protected int getInt(ConfigurationSection section, String path) {
        return section.getInt(path);
    }

    /**
     * Gets an int from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return int
     */
    protected int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    /**
     * Gets an int relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return int
     */
    protected int getInt(ConfigurationSection section, String path, int def) {
        return section.getInt(path, def);
    }

    /**
     * Gets an int list from an absolute path
     * @param path path
     * @return int list
     */
    protected List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    /**
     * Gets an int list relative to a provided section
     * @param section section
     * @param path path
     * @return int list
     */
    protected List<Integer> getIntegerList(ConfigurationSection section, String path) {
        return section.getIntegerList(path);
    }

    /**
     * Gets an itemstack from an absolute path
     * @param path path
     * @return itemstack
     */
    protected ItemStack getItemStack(String path) {
        return config.getItemStack(path);
    }

    /**
     * Gets an itemstack relative to a provided section
     * @param section section
     * @param path path
     * @return itemstack
     */
    protected ItemStack getItemStack(ConfigurationSection section, String path) {
        return section.getItemStack(path);
    }

    /**
     * Gets an itemstack from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return itemstack
     */
    protected ItemStack getItemStack(String path, ItemStack def) {
        return config.getItemStack(path, def);
    }

    /**
     * Gets an itemstack relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return
     */
    protected ItemStack getItemStack(ConfigurationSection section, String path, ItemStack def) {
        return section.getItemStack(path, def);
    }

    /**
     * Gets a long from an absolute path
     * @param path path
     * @return long
     */
    protected long getLong(String path) {
        return config.getLong(path);
    }

    /**
     * Gets a long relative to a provided section
     * @param section section
     * @param path path
     * @return long
     */
    protected long getLong(ConfigurationSection section, String path) {
        return section.getLong(path);
    }

    /**
     * Gets a long from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return long
     */
    protected long getLong(String path, long def) {
        return config.getLong(path, def);
    }

    /**
     * Gets a long relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return long
     */
    protected long getLong(ConfigurationSection section, String path, long def) {
        return section.getLong(path, def);
    }

    /**
     * Gets a long list from an absolute path
     * @param path path
     * @return long list
     */
    protected List<Long> getLongList(String path) {
        return config.getLongList(path);
    }

    /**
     * Gets a long list from a provided section
     * @param section section
     * @param path path
     * @return long list
     */
    protected List<Long> getLongList(ConfigurationSection section, String path) {
        return section.getLongList(path);
    }

    /**
     * Gets a short list from an absolute path
     * @param path path
     * @return short list
     */
    protected List<Short> getShortList(String path) {
        return config.getShortList(path);
    }

    /**
     * Gets a short list relative to a provided section
     * @param section section
     * @param path path
     * @return short list
     */
    protected List<Short> getShortList(ConfigurationSection section, String path) {
        return section.getShortList(path);
    }

    /**
     * Gets a string from an absolute path
     * @param path path
     * @return string
     */
    protected String getString(String path) {
        return config.getString(path);
    }

    /**
     * Gets a string relative to a provided section
     * @param section section
     * @param path path
     * @return string
     */
    protected String getString(ConfigurationSection section, String path) {
        return section.getString(path);
    }

    /**
     * Gets a string from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return string
     */
    protected String getString(String path, String def) {
        return config.getString(path, def);
    }

    /**
     * Gets a string relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return string
     */
    protected String getString(ConfigurationSection section, String path, String def) {
        return section.getString(path, def);
    }

    /**
     * Gets a colored string from an absolute path
     * @param path path
     * @return string
     */
    protected String getColoredString(String path) {
        return TextUtils.color(getString(path));
    }

    /**
     * Gets a colored string from a provided section
     * @param section section
     * @param path path
     * @return string
     */
    protected String getColoredString(ConfigurationSection section, String path) {
        return TextUtils.color(getString(section, path));
    }

    /**
     * Gets a colored string from an absolute path, or a default value if not found
     * @param path path
     * @param def def
     * @return string
     */
    protected String getColoredString(String path, String def) {
        return TextUtils.color(getString(path, def));
    }

    /**
     * Gets a string relative to a provided section, or a default value if not found
     * @param section section
     * @param path path
     * @param def def
     * @return string
     */
    protected String getColoredString(ConfigurationSection section, String path, String def) {
        return TextUtils.color(getString(section, path, def));
    }

    /**
     * Gets a string list from an absolute path
     * @param path path
     * @return string list
     */
    protected List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    /**
     * Gets a string list relative to a provided section
     * @param section section
     * @param path path
     * @return string list
     */
    protected List<String> getStringList(ConfigurationSection section, String path) {
        return section.getStringList(path);
    }

    /**
     * Gets a colored string list from an absolute path
     * @param path path
     * @return list string
     */
    protected List<String> getColoredStringList(String path) {
        return TextUtils.colorList(getStringList(path));
    }

    /**
     * Gets a colored string list relative to a provided path
     * @param section section
     * @param path path
     * @return string list
     */
    protected List<String> getColoredStringList(ConfigurationSection section, String path) {
        return TextUtils.colorList(getStringList(section, path));
    }

    /**
     * Parses an {@link ItemBuilder} from a YML config in the following format from an absolute path
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
    protected ItemBuilder getItemBuilder(String path) {
        return getItemBuilder(config, path);
    }

    /**
     * Parses an {@link ItemBuilder} from a YML config in the following format from a relative path
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
    protected ItemBuilder getItemBuilder(ConfigurationSection section, String path) {
        final ConfigurationSection ibSection = section.getConfigurationSection(path);

        final ItemBuilder builder = new ItemBuilder(Material.getMaterial(ibSection.getString("material")));

        // durability
        final int itemId = ibSection.getInt("item-id", Integer.MIN_VALUE);
        if (itemId != Integer.MIN_VALUE) {
            builder.durability((short) itemId);
        }

        // name
        final String name = ibSection.getString("name");
        if (name != null) {
            builder.name(TextUtils.color(name));
        }

        // lore
        final List<String> lore = ibSection.getStringList("lore");
        if (lore != null && !lore.isEmpty()) {
            builder.lore(TextUtils.colorList(lore));
        }

        // glow
        builder.glow(ibSection.getBoolean("glow"));

        // unbreakable
        builder.unbreakable(ibSection.getBoolean("unbreakable"));

        // enchantments
        final List<String> enchants = ibSection.getStringList("enchants");
        if (enchants != null && !enchants.isEmpty()) {
            for (String enchant : enchants) {
                final String[] args = enchant.split(":");
                builder.enchantment(Enchantment.getByName(args[0]), Integer.parseInt(args[1]), false);
            }
        }

        // itemflags
        final List<String> itemFlags = ibSection.getStringList("flags");
        if (itemFlags != null && !itemFlags.isEmpty()) {
            final List<ItemFlag> flags = new ArrayList<>();
            itemFlags.stream().map(ItemFlag::valueOf).forEach(flags::add);
            builder.addItemFlags(flags.toArray(new ItemFlag[]{}));
        }

        return builder;
    }

    /**
     * Parses a {@link PotionBuilder} from a YML config in the following format from an absolute path
     *
     * path:
     *  type: string # required
     *  seconds: integer # required
     *  amplifier: integer # required
     *
     * @param path path
     * @return potionBuilder
     */
    protected  PotionBuilder getPotionBuilder(String path) {
        return getPotionBuilder(config, path);
    }

    /**
     * Parses a {@link PotionBuilder} from a YML config in the following format relative to a provided section
     *
     * path:
     *  type: string # required
     *  seconds: integer # required
     *  amplifier: integer # required
     *
     * @param path path
     * @return potionBuilder
     */
    protected PotionBuilder getPotionBuilder(ConfigurationSection section, String path) {
        final ConfigurationSection childSection = section.getConfigurationSection(path);
        return new PotionBuilder().setType(PotionEffectType.getByName(childSection.getString("type"))).addSeconds(childSection.getInt("seconds")).setAdjustedAmplifier(childSection.getInt("amplifier"));
    }

    /**
     * Parses an {@link EnchantmentSet} from a YML config from an absolute path
     * @param path path (should lead to a list/array of strings)
     * @return set
     */
    protected EnchantmentSet<Enchantment> getEnchantmentSet(String path) {
        return getEnchantmentSet(config, path);
    }

    /**
     * Parses an {@link EnchantmentSet} from a YML config relative to a provided section
     * @param path path (should lead to a list/array of strings)
     * @return set
     */
    protected EnchantmentSet<Enchantment> getEnchantmentSet(ConfigurationSection section, String path) {
        return section.getStringList(path).stream().map(Enchantment::getByName).collect(Collectors.toCollection(EnchantmentSet::new));
    }

    /**
     * Parses a {@link PotionEffectTypeSet} from a YML config from an absolute path
     * @param path path (should lead to a list/array of strings)
     * @return set
     */
    protected PotionEffectTypeSet<PotionEffectType> getPotionEffectTypeSet(String path) {
        return getPotionEffectTypeSet(config, path);
    }

    /**
     * Parses a {@link PotionEffectTypeSet} from a YML config relative to a provided section
     * @param path path (should lead to a list/array of strings)
     * @return set
     */
    protected PotionEffectTypeSet<PotionEffectType> getPotionEffectTypeSet(ConfigurationSection section, String path) {
        return section.getStringList(path).stream().map(PotionEffectType::getByName).collect(Collectors.toCollection(PotionEffectTypeSet::new));
    }

    /**
     * Parses a {@link HourMinute} from a YML confing in the format "hh:mm" from an absolute path
     * @param path path
     * @return hourMinute
     */
    protected HourMinute parseHourMinute(String path) {
        return parseHourMinute(config, path);
    }

    /**
     * Parses a {@link HourMinute} from a YML confing in the format "hh:mm" relative to a provided section
     * @param path path
     * @return hourMinute
     */
    protected HourMinute parseHourMinute(ConfigurationSection section, String path) {
        final String[] args = section.getString(path).split(":");
        return HourMinute.of((byte) Integer.parseInt(args[0]), (byte) Integer.parseInt(args[1]));
    }

    /***
     * Parses a {@link Schedule} from a YML config in the following format from an absolute path
     *
     * path:
     *  - "hh:mm"
     *  - "hh:mm"
     *  - ...
     *
     * @param path path
     * @return schedule
     */
    protected Schedule getSchedule(String path) {
        return getSchedule(config, path);
    }

    /***
     * Parses a {@link Schedule} from a YML config in the following format relative to a provided path
     *
     * path:
     *  - "hh:mm"
     *  - "hh:mm"
     *  - ...
     *
     * @param path path
     * @return schedule
     */
    protected Schedule getSchedule(ConfigurationSection section, String path) {
        return new Schedule(section.getStringList(path).stream().map(hourMinute -> {
            final String[] args = hourMinute.split(":");
            return HourMinute.of((byte) Integer.parseInt(args[0]), (byte) Integer.parseInt(args[1]));
        }).collect(Collectors.toList()));
    }

    /**
     * Parses a {@link CommandFormat} from a YML config in the following format from an absolute path
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
    protected CommandFormat getCommandFormat(String path) {
        return getCommandFormat(config, path);
    }

    /**
     * Parses a {@link CommandFormat} from a YML config in the following format relative to a provided section
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
    protected CommandFormat getCommandFormat(ConfigurationSection section, String path) {
        final ConfigurationSection cfSection = section.getConfigurationSection(path);
        return new CommandFormat(
                TextUtils.color(cfSection.getString("header-left")),
                TextUtils.color(cfSection.getString("header-right")),
                TextUtils.color(cfSection.getString("title")),
                TextUtils.color(cfSection.getString("commandPrefix")),
                TextUtils.color(cfSection.getString("separator")),
                TextUtils.color(cfSection.getString("descriptionPrefix")),
                TextUtils.color(cfSection.getString("footer"))
        );
    }

}
