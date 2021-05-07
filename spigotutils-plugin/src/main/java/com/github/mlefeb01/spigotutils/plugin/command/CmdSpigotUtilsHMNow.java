package com.github.mlefeb01.spigotutils.plugin.command;

import com.github.mlefeb01.spigotutils.api.command.AbstractCommand;
import com.github.mlefeb01.spigotutils.api.object.HourMinute;
import com.github.mlefeb01.spigotutils.plugin.ConfigYml;

public final class CmdSpigotUtilsHMNow extends AbstractCommand {
    private final ConfigYml configYml;

    public CmdSpigotUtilsHMNow(ConfigYml configYml) {
        super("hmnow");

        this.configYml = configYml;

        setDescription("Sends the current hour minute");
    }

    @Override
    public String getPermission() {
        return configYml.getPermissionSpigotUtilsHMNow();
    }

    @Override
    public void execute() {
        sender.sendMessage(configYml.getMessageHMNow().replace("%now%", HourMinute.now().toString()));
    }

}
