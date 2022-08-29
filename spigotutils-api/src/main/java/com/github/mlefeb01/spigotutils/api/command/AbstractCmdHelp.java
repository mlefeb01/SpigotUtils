package com.github.mlefeb01.spigotutils.api.command;

import com.github.mlefeb01.spigotutils.api.command.parametertypes.TypeInteger;

/**
 * Sends sub-commands and their descriptions for a parent command
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractCmdHelp extends AbstractCommand {

    public AbstractCmdHelp() {
        super("help");
        addParameter(TypeInteger.getInstance(), "page", 1);
        setDescription("Sends the help command");
    }

    @Override
    public void execute() throws Exception {
        // Make the page parameter function like an optional parameter
        int page = 1;
        if (rawArgs.length > 0) {
            try {
                page = Integer.parseInt(rawArgs[1]);
            } catch (Exception e) {
                // Ignore
            }
        }
        sender.sendMessage(parent.generateHelpCommand(page));
    }

}
