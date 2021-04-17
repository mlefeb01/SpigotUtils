package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

public class TypeEntityType extends AbstractParameterType<EntityType> {
    private static final TypeEntityType instance = new TypeEntityType();

    private TypeEntityType() {}

    @Override
    public EntityType fromString(CommandSender sender, String arg) throws Exception {
        return EntityType.valueOf(arg);
    }

    public static TypeEntityType getInstance() {
        return instance;
    }

}
