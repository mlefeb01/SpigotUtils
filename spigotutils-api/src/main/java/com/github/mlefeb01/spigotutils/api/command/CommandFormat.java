package com.github.mlefeb01.spigotutils.api.command;

/**
 * Contains formatting options for the auto generated help command inside of {@link AbstractCommand}
 *
 * @author Matt Lefebvre
 */
public class CommandFormat {
    private final String headerLeft;
    private final String headerRight;
    private final String title;
    private final String commandPrefix;
    private final String separator;
    private final String descriptionPrefix;
    private final String footer;

    /**
     * Constructor
     *
     * @param headerLeft        left side of the header
     * @param headerRight       right side of the header
     * @param title             title of the header
     * @param commandPrefix     prefix before each command
     * @param separator         separates the command and the description
     * @param descriptionPrefix prefix before each description
     * @param footer            footer
     */
    public CommandFormat(String headerLeft, String headerRight, String title, String commandPrefix, String separator, String descriptionPrefix, String footer) {
        this.headerLeft = headerLeft;
        this.headerRight = headerRight;
        this.title = title;
        this.commandPrefix = commandPrefix;
        this.separator = separator;
        this.descriptionPrefix = descriptionPrefix;
        this.footer = footer;
    }

    /**
     * Getter for the left side of the header
     *
     * @return headerLeft
     */
    public String getHeaderLeft() {
        return headerLeft;
    }

    /**
     * Getter for the right side of the header
     *
     * @return headerRight
     */
    public String getHeaderRight() {
        return headerRight;
    }

    /**
     * Getter for the command title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the command prefix
     *
     * @return commandPrefix
     */
    public String getCommandPrefix() {
        return commandPrefix;
    }

    /**
     * Getter for the separator
     *
     * @return separator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Getter for the description prefix
     *
     * @return descriptionPrefix
     */
    public String getDescriptionPrefix() {
        return descriptionPrefix;
    }

    /**
     * Getter for the footer
     *
     * @return footer
     */
    public String getFooter() {
        return footer;
    }

}
