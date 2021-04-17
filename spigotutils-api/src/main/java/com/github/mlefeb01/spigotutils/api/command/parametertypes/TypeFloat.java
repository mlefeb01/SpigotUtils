package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeFloat extends AbstractParameterType<Float> {
    private static final TypeFloat instance = new TypeFloat();

    private TypeFloat() {}

    @Override
    public Float fromString(CommandSender sender, String arg) throws Exception {
        return Float.valueOf(arg);
    }

    public static TypeFloat getInstance() {
        return instance;
    }

}
