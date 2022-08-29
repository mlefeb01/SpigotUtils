package com.github.mlefeb01.spigotutils.api.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Wraps a BukkitTask and invokes the logic within the task on a defined interval. This class serves two main purposes:
 * 1: the ability to change the task frequency without canceling/restarting the task
 * 2: invoking the task relative to system time
 * <p>
 * The normal behavior for BukkitTasks is to invoke the task relative to when it was scheduled. For example, if a task is
 * scheduled to run every 5 minutes and is scheduled (initialized) on the 17th second of the current minute, the following
 * would be the next 3 executions
 * 5:17
 * 10:17
 * 15:17
 * <p>
 * This behavior is not desirable as we want the task to run exactly when it is suppose to. A task wrapped with {@link AbstractRepeatingTask}
 * will solve this issue. Using the same example, the following would be the next 3 executions after initialization
 * 5:00
 * 10:00
 * 15:00
 * <p>
 * It is important to keep in mind that if the task frequency changes between invocations, the task could potentially be
 * invoked twice in quick succession. For example, say we have a task that has a frequency of 5 seconds and its last invocation
 * was on the 45th second of some minute. If the frequency of this task was then changed to run every minute, the task would
 * be invoked 15 seconds later because that is when a task that occurs every minute would normally be invoked
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractRepeatingTask extends BukkitRunnable {
    private long next;

    public AbstractRepeatingTask() {
        next = getNext();
    }

    /**
     * Returns the epoch time this task should occur next
     *
     * @return
     */
    private long getNext() {
        final long now = System.currentTimeMillis();
        final long cooldown = getCooldownInMillis();
        return (now - (now % cooldown)) + cooldown;
    }

    @Override
    public void run() {
        final long cooldown = getCooldownInMillis();
        if (cooldown <= 0) {
            return;
        }

        final long now = System.currentTimeMillis();
        final long calcNext = getNext();
        /*
        If the cooldown for this repeating task changes between occurrences, update accordingly. Also have to make sure the
        task should not be invoked right now to ensure the change only happens between occurrences
         */
        if (now - next < 0 && next != calcNext) {
            next = calcNext;
        }
        if (now - next > 0) {
            next = calcNext;
            invoke(now);
        }
    }

    /**
     * The cooldown of this task in milliseconds
     *
     * @return cooldown
     */
    public abstract long getCooldownInMillis();

    /**
     * Invokes the task
     *
     * @param now now
     */
    public abstract void invoke(long now);

    /**
     * Starts the task
     *
     * @param plugin plugin
     */
    public void start(Plugin plugin) {
        runTaskTimer(plugin, 0, 1);
    }

}
