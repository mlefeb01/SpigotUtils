package com.github.mlefeb01.spigotutils.api.object;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Schedule object that wraps a {@link Deque} of {@link HourMinute} objects in the order they will occur relative to the
 * current time. Provides functionality to find the next {@link HourMinute} in the schedule in {@link HourMinute} form or
 * as an epoch timestamp (seconds)
 * @author Matt Lefebvre
 */
public class Schedule {
    private final Deque<HourMinute> hourMinutes;
    private HourMinute lastEvent;
    private long nextEventEpochSecond;

    /**
     * Constructor
     * @param hourMinutes times in the schedule
     */
    public Schedule(Collection<HourMinute> hourMinutes) {
        this.hourMinutes = hourMinutes.stream().sorted().collect(Collectors.toCollection(LinkedList::new));
        this.findNext();
    }

    /**
     * Finds the next {@link HourMinute} in the schedule
     */
    public void findNext() {
        if (hourMinutes.isEmpty()) {
            return;
        }

        // Move the lastEvent to the back of queue if it equals the next schedule reset
        if (hourMinutes.size() != 1 && hourMinutes.peekFirst().equals(lastEvent)) {
            hourMinutes.addLast(hourMinutes.pollFirst());
        }

        // Move the head of the deque to the back of the deque if the current time has past it
        final HourMinute now = HourMinute.now();
        if (hourMinutes.size() != 1 && (hourMinutes.peekLast().compareTo(now) > 0 || hourMinutes.peekFirst().compareTo(now) > 0)) {
            while (hourMinutes.peek().compareTo(now) < 1) {
                hourMinutes.addLast(hourMinutes.pollFirst());
            }
        }

        // Calculate the epoch time of the next time in the schedule (account for change in days as well)
        final HourMinute nextReset = hourMinutes.peek();
        ZonedDateTime nextResetZDT = nextReset.toZonedDateTime(ZoneId.systemDefault());
        if ((hourMinutes.size() == 1 && lastEvent != null) || now.compareTo(nextReset) > 0) {
            nextResetZDT = nextResetZDT.plusDays(1);
        }
        nextEventEpochSecond = nextResetZDT.toEpochSecond();
    }

    /**
     * Getter for the next {@link HourMinute} in the schedule
     * @return {@link HourMinute} of next time
     */
    public HourMinute getNext() {
        return this.hourMinutes.peekFirst();
    }

    /**
     * Getter for the epoch timestamp (in seconds) of the next time in the schedule
     * @return epoch time in seconds
     */
    public long getNextEpoch() {
        return this.nextEventEpochSecond;
    }

    /**
     * Sets the last occurrence of the schedule to the next {@link HourMinute} in the schedule. This method should be called
     * after isNextEventTime()
     */
    public void setLastEvent() {
        this.lastEvent = this.hourMinutes.peekFirst();
    }

    /**
     * Determines if the current time is the same time as the next {@link HourMinute} in the schedule
     * @return boolean if the time is the next event time
     */
    public boolean isNextEventTime() {
        final HourMinute now = HourMinute.now();
        if (!now.equals(getNext())) {
            return false;
        // Special case where there is only 1 time in the schedule so the reset would happen on a different day
        } else if (now.equals(this.lastEvent) && (this.nextEventEpochSecond - (System.currentTimeMillis() / 1000)) > 0) {
            return false;
        }
        return true;
    }

}
