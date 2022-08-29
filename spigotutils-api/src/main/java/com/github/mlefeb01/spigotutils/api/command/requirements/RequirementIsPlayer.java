package com.github.mlefeb01.spigotutils.api.command.requirements;

import com.github.mlefeb01.spigotutils.api.command.AbstractRequirement;
import com.github.mlefeb01.spigotutils.api.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RequirementIsPlayer extends AbstractRequirement {
    private static final RequirementIsPlayer instance = new RequirementIsPlayer();

    private RequirementIsPlayer() {
    }

    @Override
    public boolean isMet(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public String getErrorMessage() {
        return TextUtils.color("&cYou must be a player");
    }

    public static RequirementIsPlayer getInstance() {
        return instance;
    }

}
