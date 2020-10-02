package com.github.mlefeb01.spigotutils.api.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.stream.Collectors;

import static org.bukkit.enchantments.Enchantment.*;
import static org.bukkit.potion.PotionEffectType.*;

/**
 * Methods to manipulate strings/format other data into easily readable formats
 *
 * @author Matt Lefebvre
 */
public final class TextUtils {

    private TextUtils() {
        throw new AssertionError();
    }

    /**
     * Replaces {@literal &} with the corresponding native minecraft color code
     *
     * @param string string
     * @return colored string
     */
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Replaces {@literal &} with the corresponding native minecraft color code for every string in the list
     *
     * @param list list
     * @return list
     */
    public static List<String> colorList(List<String> list) {
        return list.stream().map(TextUtils::color).collect(Collectors.toList());
    }

    /**
     * Formats an {@link Enum} by replacing underscores with spaces and capitalizing each word
     * (e.g. - PIG_ZOMBIE to "Pig Zombie", IRON_GOLEM to "Iron Golem", SHEEP to "Sheep")
     *
     * @param enumeration enum
     * @return formatted enum
     */
    public static String formatEnumAsString(Enum enumeration) {
        return capitalizeFully(enumeration.name().replace("_", " ").toLowerCase());
    }

    /**
     * Capitalizes every word in a String
     *
     * @param str string
     * @return capitalized string
     */
    public static String capitalizeFully(String str) {
        final String[] splitString = str.split(" ");
        for (int n = 0; n < splitString.length; n++) {
            final String temp = splitString[n];
            // If the string is more than 1 letter append the first letter of it capitalize to the rest of the word. Else, upper case the single character
            splitString[n] = (temp.length() > 1) ? temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase() : temp.toUpperCase();
        }
        return String.join(" ", splitString);
    }

    /**
     * Formats a double currency into a readable format
     * If the number is in the billions it will return how many billion with 2 decimal precision (e.g. - 2.43B)
     * If the number is in the millions it will return how many million with 2 decimal precision (e.g. - 1.35M)
     * If the number is in the thousands it will return how many thousand with 2 decimal precision (e.g. - 100.34k)
     * If the number is less than one thousand it will return the number with 2 decimal precision (e.g. - 465.52)
     *
     * @param currency currency
     * @return formatted currency
     */
    public static String formatCurrency(double currency) {
        if (currency >= 1000000000.0) {
            return String.format("%.2f", currency / 1000000000.0) + "B";
        } else if (currency >= 1000000.0) {
            return String.format("%.2f", currency / 1000000.0) + "M";
        } else if (currency >= 1000.0) {
            return String.format("%.2f", currency / 1000.0) + "k";
        } else {
            return String.format("%.2f", currency);
        }
    }

    /**
     * Formats an int currency into a readable format
     * If the number is in the billions it will return how many billion with 2 decimal precision (e.g. - 2.43B)
     * If the number is in the millions it will return how many million with 2 decimal precision (e.g. - 1.35M)
     * If the number is in the thousands it will return how many thousand with 2 decimal precision (e.g. - 100.34k)
     * If the number is less than one thousand it will return the number with 2 decimal precision (e.g. - 465.52)
     *
     * @param currency currency
     * @return formatted currency
     */
    public static String formatCurrency(long currency) {
        if (currency >= 1000000000.0) {
            return String.format("%.2f", currency / 1000000000.0) + "B";
        } else if (currency >= 1000000.0) {
            return String.format("%.2f", currency / 1000000.0) + "M";
        } else if (currency >= 1000.0) {
            return String.format("%.2f", currency / 1000.0) + "k";
        } else {
            return "" + currency;
        }
    }

    /**
     * Formats an int currency into a readable format
     * If the number is in the billions it will return how many billion with 2 decimal precision (e.g. - 2.43B)
     * If the number is in the millions it will return how many million with 2 decimal precision (e.g. - 1.35M)
     * If the number is in the thousands it will return how many thousand with 2 decimal precision (e.g. - 100.34k)
     * If the number is less than one thousand it will return the number with 2 decimal precision (e.g. - 465.52)
     *
     * @param currency currency
     * @return formatted currency
     */
    public static String formatCurrency(int currency) {
        return formatCurrency((long) currency);
    }

    /**
     * Turns a number representing seconds into a readable format
     *
     * @param seconds seconds
     * @return xd, xh, xm, xs
     */
    public static String formatSecondsAsTime(int seconds) {
        int d, h, m;

        d = seconds / 86400;
        seconds -= d * 86400;

        h = seconds / 3600;
        seconds -= h * 3600;

        m = seconds / 60;
        seconds -= m * 60;

        if (d > 0) {
            return String.format("%dd, %dh, %dm, %ds", d, h, m, seconds);
        } else if (h > 0) {
             return String.format("%dh, %dm, %ds", h, m, seconds);
        } else if (m > 0) {
             return String.format("%dm, %ds", m, seconds);
        } else {
            return seconds + "s";
        }
    }

    /**
     * Formats the time passed in a readable format. This method should be used when something has already occurred and
     * you want to format the time that has passed since
     *
     * @param past a time in the past
     * @return xd, xh, xm, xs
     */
    public static String formatTimePast(long past) {
        return formatSecondsAsTime((int) ((System.currentTimeMillis() - past) / 1000));

    }

    /**
     * Formats the time remaining until a time is reached as a readable format. This method should be used when something
     * will occur in the future and you want to format the time remaining until
     *
     * @param end the stop time
     * @return xd, xh, xm, xs
     */
    public static String formatTimeUntil(long end) {
        return formatSecondsAsTime((int) ((end - System.currentTimeMillis()) / 1000));
    }

    /**
     * Creates a progress bar
     * precondition: progress less than or equal to goal and goal greater than 0
     *
     * @param progress the current progress
     * @param goal the goal (max possible progress)
     * @param bars size of the progress bar
     * @param barSymbol the symbol to use for the bar
     * @param progressColor current progress color
     * @param remainingColor remaining progress color
     * @return progress bar
     */
    public static String createProgressBar(double progress, double goal, int bars, char barSymbol, String progressColor, String remainingColor) {
        if (progress > goal || bars <= 0) {
            return "";
        }

        final int divisor = (int) goal / bars;
        final int adjustedProgress = ((int) progress - ((int) progress % divisor)) / divisor;

        final StringBuilder builder = new StringBuilder();
        builder.append(progressColor);
        for (int n = 0; n < adjustedProgress; n++) {
            builder.append(barSymbol);
        }
        builder.append(remainingColor);
        for (int n = adjustedProgress; n < bars; n++) {
            builder.append(barSymbol);
        }

        return builder.toString();
    }

    /**
     * Creates a progress bar
     * precondition: progress less than or equal to goal and goal greater than 0
     *
     * @param progress the current progress
     * @param goal the goal (max possible progress)
     * @param bars size of the progress bar
     * @param barSymbol the symbol to use for the bar
     * @param progressColor current progress color
     * @param remainingColor remaining progress color
     * @return progress bar
     */
    public static String createProgressBar(double progress, double goal, int bars, char barSymbol, ChatColor progressColor, ChatColor remainingColor) {
        return createProgressBar(progress, goal, bars, barSymbol, ChatColor.COLOR_CHAR + "" + progressColor.getChar(), ChatColor.COLOR_CHAR + "" + remainingColor.getChar());
    }

    /**
     * Returns a nice, readable name for a potion effect type
     *
     * @param type type
     * @return nice name
     */
    public static String getPotionNiceName(PotionEffectType type) {
        if (type == SPEED) {
            return "Speed";
        } else if (type == SLOW) {
            return "Slowness";
        } else if (type == FAST_DIGGING) {
            return "Haste";
        } else if (type == SLOW_DIGGING) {
            return "Mining Fatigue";
        } else if (type == INCREASE_DAMAGE) {
            return "Strength";
        } else if (type == HEAL) {
            return "Instant Heal";
        } else if (type == HARM) {
            return "Instant Damage";
        } else if (type == JUMP) {
            return "Jump";
        } else if (type == CONFUSION) {
            return "Nausea";
        } else if (type == REGENERATION) {
            return "Regeneration";
        } else if (type == DAMAGE_RESISTANCE) {
            return "Resistance";
        } else if (type == FIRE_RESISTANCE) {
            return "Fire Resistance";
        } else if (type == WATER_BREATHING) {
            return "Water Breathing";
        } else if (type == INVISIBILITY) {
            return "Invisibility";
        } else if (type == BLINDNESS) {
            return "Blindness";
        } else if (type == NIGHT_VISION) {
            return "Night Vision";
        } else if (type == HUNGER) {
            return "Hunger";
        } else if (type == WEAKNESS) {
            return "Weakness";
        } else if (type == POISON) {
            return "Poison";
        } else if (type == WITHER) {
            return "Wither";
        } else if (type == HEALTH_BOOST) {
            return "Health Boost";
        } else if (type == ABSORPTION) {
            return "Absorption";
        } else if (type == SATURATION) {
            return "Saturation";
        } else {
            return "Error";
        }
    }

    /**
     * Returns a nice, readable name for an enchant
     *
     * @param enchant enchant
     * @return nice name
     */
    public static String getEnchantNiceName(Enchantment enchant) {
        if (enchant.equals(PROTECTION_ENVIRONMENTAL)) {
            return "Protection";
        } else if (enchant.equals(PROTECTION_FIRE)) {
            return "Fire Protection";
        } else if (enchant.equals(PROTECTION_FALL)) {
            return "Feather Falling";
        } else if (enchant.equals(PROTECTION_EXPLOSIONS)) {
            return "Blast Protection";
        } else if (enchant.equals(PROTECTION_PROJECTILE)) {
            return "Projectile Protection";
        } else if (enchant.equals(OXYGEN)) {
            return "Respiration";
        } else if (enchant.equals(WATER_WORKER)) {
            return "Aqua Affinity";
        } else if (enchant.equals(THORNS)) {
            return "Thorns";
        } else if (enchant.equals(DEPTH_STRIDER)) {
            return "Depth Strider";
        } else if (enchant.equals(DAMAGE_ALL)) {
            return "Sharpness";
        } else if (enchant.equals(DAMAGE_UNDEAD)) {
            return "Smite";
        } else if (enchant.equals(DAMAGE_ARTHROPODS)) {
            return "Bane of Arthropods";
        } else if (enchant.equals(KNOCKBACK)) {
            return "Knockback";
        } else if (enchant.equals(FIRE_ASPECT)) {
            return "Fire Aspect";
        } else if (enchant.equals(LOOT_BONUS_MOBS)) {
            return "Looting";
        } else if (enchant.equals(DIG_SPEED)) {
            return "Efficiency";
        } else if (enchant.equals(SILK_TOUCH)) {
            return "Silk Touch";
        } else if (enchant.equals(DURABILITY)) {
            return "Unbreaking";
        } else if (enchant.equals(LOOT_BONUS_BLOCKS)) {
            return "Fortune";
        } else if (enchant.equals(ARROW_DAMAGE)) {
            return "Power";
        } else if (enchant.equals(ARROW_KNOCKBACK)) {
            return "Punch";
        } else if (enchant.equals(ARROW_FIRE)) {
            return "Flame";
        } else if (enchant.equals(ARROW_INFINITE)) {
            return "Infinity";
        } else if (enchant.equals(LUCK)) {
            return "Luck";
        } else if (enchant.equals(LURE)) {
            return "Lure";
        } else {
            return "Error";
        }
    }

}
