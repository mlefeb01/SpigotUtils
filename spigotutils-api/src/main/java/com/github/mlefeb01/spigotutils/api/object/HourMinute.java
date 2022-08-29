package com.github.mlefeb01.spigotutils.api.object;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provides a time object with hour/minute precision and a variety of utility methods such as converting from and to
 * {@link ZonedDateTime} objects
 *
 * @author Matt Lefebvre
 */
public class HourMinute implements Comparable<HourMinute> {
    /**
     * Hour of the day, 0-23 inclusive
     */
    private final byte hour;
    /**
     * Minute of the hour, 0-59 inclusive
     */
    private final byte minute;

    /**
     * Constructor
     *
     * @param hour   hour, 0-23 inclusive
     * @param minute minute of the hour, 0-59 inclusive
     */
    public HourMinute(byte hour, byte minute) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be: 0 <= hour <= 23");
        } else if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be: 0 <= minute <= 59");
        }

        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the hour of the HourMinute
     *
     * @return hour 0-23 inclusive
     */
    public byte getHour() {
        return hour;
    }

    /**
     * Returns the minute of the hour of this HourMinute
     *
     * @return minute 0-59 inclusive
     */
    public byte getMinute() {
        return minute;
    }

    /**
     * Converts an instance of HourMinute into a ZonedDateTime object
     *
     * @return ZonedDateTime
     */
    public ZonedDateTime toZonedDateTime(ZoneId zoneId) {
        final ZonedDateTime now = ZonedDateTime.now();
        return ZonedDateTime.of(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                this.getHour(),
                this.getMinute(),
                0,
                0,
                zoneId
        );
    }

    @Override
    public String toString() {
        return String.format("%d:%d", hour, minute);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HourMinute)) {
            return false;
        }

        final HourMinute hourMinute = (HourMinute) object;
        return (this.hour == hourMinute.hour) && (this.minute == hourMinute.minute);
    }

    @Override
    public int hashCode() {
        final int result = 17;
        final int c = hour + minute;
        return 31 * result * c;
    }

    @Override
    public int compareTo(HourMinute hourMinute) {
        if (this.hour == hourMinute.hour && this.minute == hourMinute.minute) {
            return 0;
        } else if (this.hour > hourMinute.hour) {
            return 1;
        } else if (this.hour < hourMinute.hour) {
            return -1;
        } else if (this.minute > hourMinute.minute) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Static factory method transforming the {@link ZonedDateTime} from the time this method is invoked
     * into an {@link} HourMinute object
     *
     * @return HourMinute
     */
    public static HourMinute now() {
        return of(ZonedDateTime.now());
    }

    /**
     * Static factory method transforming a {@link ZonedDateTime} into an {@link HourMinute}
     *
     * @param zonedDateTime ZonedDateTime
     * @return HourMinute
     */
    public static HourMinute of(ZonedDateTime zonedDateTime) {
        return new HourMinute((byte) zonedDateTime.getHour(), (byte) zonedDateTime.getMinute());
    }

    /**
     * Static factory method creating a new HourMinute instance given an hour/minute. This is no different than calling
     * the constructor
     *
     * @param hour   hour 0-23 inclusive
     * @param minute minute 0-59 inclusive
     * @return HourMinute
     */
    public static HourMinute of(byte hour, byte minute) {
        return new HourMinute(hour, minute);
    }

    /**
     * Static factory method parsing the String obtained from an HourMinute's toString method
     *
     * @param string ZonedDateTime#toString()
     * @return ZonedDateTime
     */
    public static HourMinute parse(String string) {
        final String[] args = string.split(":");
        return new HourMinute(Byte.parseByte(args[0]), Byte.parseByte(args[1]));
    }

}
