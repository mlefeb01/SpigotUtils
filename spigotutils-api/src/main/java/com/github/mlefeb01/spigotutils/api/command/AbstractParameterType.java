package com.github.mlefeb01.spigotutils.api.command;

import org.bukkit.command.CommandSender;

/**
 * Models a parameter for a command. Encapsulates logic to convert the raw argument (String) into its respective type
 * @param <T> type
 * @author Matt Lefebvre
 */
public abstract class AbstractParameterType<T> {

    /**
     * Converts a raw arg (String) into a different type
     * @param sender sender
     * @param arg arg
     * @return translated arg
     * @throws Exception exception
     */
    public abstract T fromString(CommandSender sender, String arg) throws Exception;

}
