package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeShort extends AbstractParameterType<Short> {
    private static final TypeShort instance = new TypeShort();

    private TypeShort() {}

    @Override
    public Short fromString(CommandSender sender, String arg) throws Exception {
        return Short.valueOf(arg);
    }

    public static TypeShort getInstance() {
        return instance;
    }

}
