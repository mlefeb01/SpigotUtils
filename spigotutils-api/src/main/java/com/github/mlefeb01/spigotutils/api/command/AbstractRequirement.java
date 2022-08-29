package com.github.mlefeb01.spigotutils.api.command;

import org.bukkit.command.CommandSender;

/**
 * A command requirement, checked before the command is executed
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractRequirement {

    /**
     * Checks if the sender meets the requirement
     *
     * @param sender sender
     * @return true/false
     */
    public abstract boolean isMet(CommandSender sender);

    /**
     * The error message sent to the sender if this requirement is not met
     *
     * @return message
     */
    public abstract String getErrorMessage();

}
