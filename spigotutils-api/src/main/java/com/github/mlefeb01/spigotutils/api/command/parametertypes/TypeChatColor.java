package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TypeChatColor extends AbstractParameterType<ChatColor> {
    private static final TypeChatColor instance = new TypeChatColor();

    private TypeChatColor() {}

    @Override
    public ChatColor fromString(CommandSender sender, String arg) throws Exception {
        return ChatColor.valueOf(arg);
    }

    public static TypeChatColor getInstance() {
        return instance;
    }

}
