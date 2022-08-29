package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeLong extends AbstractParameterType<Long> {
    private static final TypeLong instance = new TypeLong();

    private TypeLong() {
    }

    @Override
    public Long fromString(CommandSender sender, String arg) throws Exception {
        return Long.valueOf(arg);
    }

    public static TypeLong getInstance() {
        return instance;
    }

}
