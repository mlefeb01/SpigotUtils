package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;

public class TypeByte extends AbstractParameterType<Byte> {
    private static final TypeByte instance = new TypeByte();

    private TypeByte() {}

    @Override
    public Byte fromString(CommandSender sender, String arg) throws Exception {
        return Byte.valueOf(arg);
    }

    public static TypeByte getInstance() {
        return instance;
    }

}
