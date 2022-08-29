package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeBoolean extends AbstractParameterType<Boolean> {
    private static final TypeBoolean instance = new TypeBoolean();

    private TypeBoolean() {
    }

    @Override
    public Boolean fromString(CommandSender sender, String arg) throws Exception {
        return Boolean.valueOf(arg);
    }

    public static TypeBoolean getInstance() {
        return instance;
    }

}
