package com.github.mlefeb01.spigotutils.plugin.command;

import com.github.mlefeb01.spigotutils.api.command.AbstractCmdReload;
import com.github.mlefeb01.spigotutils.plugin.ConfigYml;

public final class CmdSpigotUtilsReload extends AbstractCmdReload {
    private final ConfigYml configYml;

    public CmdSpigotUtilsReload(ConfigYml configYml) {
        super(configYml);

        this.configYml = configYml;
    }

    @Override
    public String getPermission() {
        return configYml.getPermissionSpigotUtilsReload();
    }

}
