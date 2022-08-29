package com.github.mlefeb01.spigotutils.api.task;

import com.github.mlefeb01.spigotutils.api.object.HourMinute;
import com.github.mlefeb01.spigotutils.api.object.Schedule;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

/**
 * Wraps a BukkitTask that invokes when scheduled provided a {@link Schedule}. Handles updating the schedule when the
 * next event in the schedule occurs.
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractScheduledTask extends BukkitRunnable {

    @Override
    public void run() {
        final Schedule schedule = getSchedule();

        // Schedule is empty, do nothing because nothing is scheduled
        final HourMinute next = schedule.getNext();
        if (next == null) {
            return;
        }

        // Either broadcast the countdown, invoke the task, or do nothing
        final int seconds = (int) (schedule.getNextEpoch() - (System.currentTimeMillis() / 1000));
        if (getCountdownSeconds().contains(seconds)) {
            Bukkit.broadcastMessage(getCountdownBroadcast().replace("%remaining%", TextUtils.formatSecondsAsTime(seconds)));
        } else if (schedule.isNextEventTime()) {
            schedule.update();
            invoke();
        }
    }

    /**
     * The schedule of times this task will be invoked
     *
     * @return schedule
     */
    public abstract Schedule getSchedule();

    /**
     * The set of seconds remaining until the next invocation of this task
     *
     * @return set
     */
    public abstract Set<Integer> getCountdownSeconds();

    /**
     * The broadcast sent when a certain number of seconds remaining is met (%remaining% is the time placeholder)
     *
     * @return broadcast
     */
    public abstract String getCountdownBroadcast();

    /**
     * Invokes the task
     */
    public abstract void invoke();

    /**
     * Initializes the task
     *
     * @param plugin
     */
    public void start(Plugin plugin) {
        runTaskTimer(plugin, 0, 20);
    }

}