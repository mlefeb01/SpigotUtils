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
 *
 * Upon creation of a Schedule, an O(n^2) preprocess occurs where the given collection of {@link HourMinute} is streamed
 * and sorted into a {@link LinkedList} in ascending order from 00:00 to 23:59. Then, findNext is called to sort the
 * schedule once again but now relative to the current time. It will detach the head and append it to the deque until
 * the head of the deque is the next {@link HourMinute} that will occur relative to when the sort occurs. This O(n^2)
 * preprocess is necessary because it allows us to achieve O(1) findNext() calls after the preprocess. The deque of
 * hourMinutes needs to be sorted to achieve that constant findNext() runtime, because after the initialization updates
 * generally just detach the head and append it to the deque.
 *
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
     * Getter for the next {@link HourMinute} in the schedule. Null if the schedule is empty
     * @return {@link HourMinute} of next time
     */
    public HourMinute getNext() {
        return this.hourMinutes.peekFirst();
    }

    /**
     * Getter for the epoch timestamp (in seconds) of the next time in the schedule. 0 if the schedule is empty
     * @return epoch time in seconds
     */
    public long getNextEpoch() {
        return this.nextEventEpochSecond;
    }

    /**
     * Sets the last occurrence of the schedule to the next {@link HourMinute} in the schedule. This method should only
     * be called after isNextEventTime()
     */
    public void setLastEvent() {
        this.lastEvent = this.hourMinutes.peekFirst();
    }

    /**
     * Updates the schedule, this should be called after isNextEventTime() returns true. This method will set the lastEvent
     * reference and update the next event in the schedule
     */
    public void update() {
        setLastEvent();
        findNext();
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
        } else if (now.equals(this.lastEvent) && getSecondsUntilNext() > 0) {
            return false;
        }
        return true;
    }

    /**
     * Returns the number of seconds until the next time in the schedule
     * @return int seconds
     */
    public int getSecondsUntilNext() {
        return (int) (this.nextEventEpochSecond - (System.currentTimeMillis() / 1000));
    }

    /**
     * Returns if the schedule is empty
     * @return empty
     */
    public boolean isEmpty() {
        return this.hourMinutes.isEmpty();
    }

    /**
     * Static factory method
     * @param hourMinutes times in the schedule
     * @return schedule
     */
    public static Schedule of(Collection<HourMinute> hourMinutes) {
        return new Schedule(hourMinutes);
    }

}
