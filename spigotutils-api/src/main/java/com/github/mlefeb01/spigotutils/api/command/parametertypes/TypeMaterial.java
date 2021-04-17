package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class TypeMaterial extends AbstractParameterType<Material> {
    private static final TypeMaterial instance = new TypeMaterial();

    private TypeMaterial() {}

    @Override
    public Material fromString(CommandSender sender, String arg) throws Exception {
        return Material.valueOf(arg);
    }

    public static TypeMaterial getInstance() {
        return instance;
    }

}
