package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class TypeOfflinePlayer extends AbstractParameterType<OfflinePlayer> {
    private static final TypeOfflinePlayer instance = new TypeOfflinePlayer();

    private TypeOfflinePlayer() {}

    @Override
    public OfflinePlayer fromString(CommandSender sender, String arg) throws Exception {
        return Bukkit.getOfflinePlayer(arg);
    }

    public static TypeOfflinePlayer getInstance() {
        return instance;
    }

}
