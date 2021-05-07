package com.github.mlefeb01.spigotutils.plugin.command;

import com.github.mlefeb01.spigotutils.api.command.AbstractCommand;
import com.github.mlefeb01.spigotutils.plugin.ConfigYml;

public final class CmdSpigotUtils extends AbstractCommand {
    private final ConfigYml configYml;

    public CmdSpigotUtils(ConfigYml configYml) {
        super("spigotutils");

        this.configYml = configYml;

        addChild(new CmdSpigotUtilsHelp(configYml));
        addChild(new CmdSpigotUtilsReload(configYml));
    }

    @Override
    public String getPermission() {
        return configYml.getPermissionSpigotUtils();
    }

}
