package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeString extends AbstractParameterType<String> {
    private static final TypeString instance = new TypeString();

    private TypeString() {
    }

    @Override
    public String fromString(CommandSender sender, String arg) throws Exception {
        return arg;
    }

    public static TypeString getInstance() {
        return instance;
    }

}
