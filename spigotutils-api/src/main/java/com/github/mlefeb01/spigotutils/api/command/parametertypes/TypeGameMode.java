package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

public class TypeGameMode extends AbstractParameterType<GameMode> {
    private static final TypeGameMode instance = new TypeGameMode();

    private TypeGameMode() {}

    @Override
    public GameMode fromString(CommandSender sender, String arg) throws Exception {
        return GameMode.valueOf(arg);
    }

    public static TypeGameMode getInstance() {
        return instance;
    }

}
