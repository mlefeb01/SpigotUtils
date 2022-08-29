package com.github.mlefeb01.spigotutils.api.command;

import com.github.mlefeb01.spigotutils.api.config.AbstractConfig;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;

/**
 * Common sub-command that reloads a {@link AbstractConfig} and notifies the sender
 *
 * @author Matt Lefebvre
 */
public abstract class AbstractCmdReload extends AbstractCommand {
    private final AbstractConfig config;

    public AbstractCmdReload(AbstractConfig config) {
        super("reload");
        this.config = config;
        setDescription(String.format("Reloads the following config file (%s - %s)", config.getPlugin().getName(), config.getFileName()));
    }

    @Override
    public void execute() throws Exception {
        config.load();
        sender.sendMessage(TextUtils.color(String.format("&a%s (%s) has been reloaded", config.getPlugin().getName(), config.getFileName())));
    }

}
