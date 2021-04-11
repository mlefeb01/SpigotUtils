package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

public class TypeEnchantment extends AbstractParameterType<Enchantment> {
    private static final TypeEnchantment instance = new TypeEnchantment();

    private TypeEnchantment() {}

    @Override
    public Enchantment fromString(CommandSender sender, String arg) throws Exception {
        return Enchantment.getByName(arg);
    }

    public static TypeEnchantment getInstance() {
        return instance;
    }

}
