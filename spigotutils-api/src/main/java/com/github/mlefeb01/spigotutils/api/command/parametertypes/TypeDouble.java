package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeDouble extends AbstractParameterType<Double> {
    private static final TypeDouble instance = new TypeDouble();

    private TypeDouble() {
    }

    @Override
    public Double fromString(CommandSender sender, String arg) throws Exception {
        return Double.valueOf(arg);
    }

    public static TypeDouble getInstance() {
        return instance;
    }

}
