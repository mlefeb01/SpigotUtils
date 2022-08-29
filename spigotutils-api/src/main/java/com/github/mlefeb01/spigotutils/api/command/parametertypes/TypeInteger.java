package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeInteger extends AbstractParameterType<Integer> {
    private static final TypeInteger instance = new TypeInteger();

    private TypeInteger() {
    }

    @Override
    public Integer fromString(CommandSender sender, String arg) throws Exception {
        return Integer.valueOf(arg);
    }

    public static TypeInteger getInstance() {
        return instance;
    }

}
