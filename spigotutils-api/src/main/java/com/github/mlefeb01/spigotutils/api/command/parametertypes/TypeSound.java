package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;

public class TypeSound extends AbstractParameterType<Sound> {
    private static final TypeSound instance = new TypeSound();

    private TypeSound() {}

    @Override
    public Sound fromString(CommandSender sender, String arg) throws Exception {
        return Sound.valueOf(arg);
    }

    public static TypeSound getInstance() {
        return instance;
    }

}
