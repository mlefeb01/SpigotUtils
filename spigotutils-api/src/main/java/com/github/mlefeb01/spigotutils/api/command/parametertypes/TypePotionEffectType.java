package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

public class TypePotionEffectType extends AbstractParameterType<PotionEffectType> {
    private static final TypePotionEffectType instance = new TypePotionEffectType();

    private TypePotionEffectType() {
    }

    @Override
    public PotionEffectType fromString(CommandSender sender, String arg) throws Exception {
        return PotionEffectType.getByName(arg);
    }

    public static TypePotionEffectType getInstance() {
        return instance;
    }

}
