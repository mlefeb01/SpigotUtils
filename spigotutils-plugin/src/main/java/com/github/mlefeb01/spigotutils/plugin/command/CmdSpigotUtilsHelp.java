package com.github.mlefeb01.spigotutils.plugin.command;

import com.github.mlefeb01.spigotutils.api.command.AbstractCmdHelp;
import com.github.mlefeb01.spigotutils.plugin.ConfigYml;

public final class CmdSpigotUtilsHelp extends AbstractCmdHelp {
    private final ConfigYml configYml;

    public CmdSpigotUtilsHelp(ConfigYml configYml) {
        this.configYml = configYml;
    }

    @Override
    public String getPermission() {
        return configYml.getPermissionSpigotUtilsHelp();
    }

}
