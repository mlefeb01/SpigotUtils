package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.Effect;
import org.bukkit.command.CommandSender;

public class TypeEffect extends AbstractParameterType<Effect> {
    private static final TypeEffect instance = new TypeEffect();

    private TypeEffect() {}

    @Override
    public Effect fromString(CommandSender sender, String arg) throws Exception {
        return Effect.valueOf(arg);
    }

    public static TypeEffect getInstance() {
        return instance;
    }

}
