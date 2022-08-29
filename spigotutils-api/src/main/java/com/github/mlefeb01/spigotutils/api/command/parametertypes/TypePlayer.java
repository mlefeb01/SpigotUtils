package com.github.mlefeb01.spigotutils.api.command.parametertypes;

import com.github.mlefeb01.spigotutils.api.command.AbstractParameterType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TypePlayer extends AbstractParameterType<Player> {
    private static final TypePlayer instance = new TypePlayer();

    private TypePlayer() {
    }

    @Override
    public Player fromString(CommandSender sender, String arg) throws Exception {
        final Player player = Bukkit.getPlayer(arg);
        if (player == null) {
            throw new RuntimeException(String.format("%s is not a valid/online player!", arg));
        }
        return player;
    }

    public static TypePlayer getInstance() {
        return instance;
    }

}
