package com.github.mlefeb01.spigotutils.api.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bukkit.enchantments.Enchantment.*;
import static org.bukkit.potion.PotionEffectType.*;

/**
 * Methods to manipulate strings/format other data into easily readable formats
 *
 * @author Matt Lefebvre
 */
public final class TextUtils {
    private static final Map<Character, Integer> SYMBOLS = new HashMap<>();

    static {
        SYMBOLS.put('M', 1000);
        SYMBOLS.put('D', 500);
        SYMBOLS.put('C', 100);
        SYMBOLS.put('L', 50);
        SYMBOLS.put('X', 10);
        SYMBOLS.put('V', 5);
        SYMBOLS.put('I', 1);
    }

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
     * @param progress       the current progress
     * @param goal           the goal (max possible progress)
     * @param bars           size of the progress bar
     * @param barSymbol      the symbol to use for the bar
     * @param progressColor  current progress color
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
     * @param progress       the current progress
     * @param goal           the goal (max possible progress)
     * @param bars           size of the progress bar
     * @param barSymbol      the symbol to use for the bar
     * @param progressColor  current progress color
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
        if (type.equals(SPEED)) {
            return "Speed";
        } else if (type.equals(SLOW)) {
            return "Slowness";
        } else if (type.equals(FAST_DIGGING)) {
            return "Haste";
        } else if (type.equals(SLOW_DIGGING)) {
            return "Mining Fatigue";
        } else if (type.equals(INCREASE_DAMAGE)) {
            return "Strength";
        } else if (type.equals(HEAL)) {
            return "Instant Heal";
        } else if (type.equals(HARM)) {
            return "Instant Damage";
        } else if (type.equals(JUMP)) {
            return "Jump";
        } else if (type.equals(CONFUSION)) {
            return "Nausea";
        } else if (type.equals(REGENERATION)) {
            return "Regeneration";
        } else if (type.equals(DAMAGE_RESISTANCE)) {
            return "Resistance";
        } else if (type.equals(FIRE_RESISTANCE)) {
            return "Fire Resistance";
        } else if (type.equals(WATER_BREATHING)) {
            return "Water Breathing";
        } else if (type.equals(INVISIBILITY)) {
            return "Invisibility";
        } else if (type.equals(BLINDNESS)) {
            return "Blindness";
        } else if (type.equals(NIGHT_VISION)) {
            return "Night Vision";
        } else if (type.equals(HUNGER)) {
            return "Hunger";
        } else if (type.equals(WEAKNESS)) {
            return "Weakness";
        } else if (type.equals(POISON)) {
            return "Poison";
        } else if (type.equals(WITHER)) {
            return "Wither";
        } else if (type.equals(HEALTH_BOOST)) {
            return "Health Boost";
        } else if (type.equals(ABSORPTION)) {
            return "Absorption";
        } else if (type.equals(SATURATION)) {
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

    /**
     * Formats an integer (comma separated)
     *
     * @param num num
     * @return formatted num
     */
    public static String formatInt(int num) {
        return formatLong(num);
    }

    /**
     * Formats a long (comma separated)
     *
     * @param num num
     * @return formatted num
     */
    public static String formatLong(long num) {
        return String.format("%,d", num);
    }

    /**
     * Formats a double (comma separated)
     *
     * @param num           num
     * @param decimalPlaces decimalPlaces
     * @return formatted num
     */
    public static String formatDouble(double num, int decimalPlaces) {
        final String inner = "%,." + decimalPlaces + "f";
        return String.format(inner, num);
    }

    /**
     * Converts a String that was created by TextUtils#intToRoman back to its integer form
     *
     * @param roman roman
     * @return int
     */
    public static int convertRomanNumeral(String roman) {
        String modRoman = roman.replace("CM", "DCCCC");
        modRoman = modRoman.replace("CD", "CCCC");
        modRoman = modRoman.replace("XC", "LXXXX");
        modRoman = modRoman.replace("XL", "XXXX");
        modRoman = modRoman.replace("IX", "VIIII");
        modRoman = modRoman.replace("IV", "IIII");

        char[] chars = modRoman.toCharArray();
        int total = 0;
        for (char c : chars) {
            total += SYMBOLS.get(c);
        }

        return total;
    }

    /**
     * Converts an integer to its roman numeral
     *
     * @param num num
     * @return roman numeral
     */
    public static String intToRoman(int num) {
        if (num == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        int times = 0;

        String[] romans = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] ints = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        for (int i = ints.length - 1; i >= 0; i--) {
            times = num / ints[i];
            num %= ints[i];
            while (times > 0) {
                sb.append(romans[i]);
                times--;
            }
        }
        return sb.toString();
    }

}
