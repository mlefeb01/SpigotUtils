package com.github.mlefeb01.spigotutils.api.command.requirements;

import com.github.mlefeb01.spigotutils.api.command.AbstractRequirement;
import com.github.mlefeb01.spigotutils.api.utils.ItemUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RequirementItemInHand extends AbstractRequirement {
    private static final RequirementItemInHand instance = new RequirementItemInHand();

    private RequirementItemInHand() {}

    @Override
    public boolean isMet(CommandSender sender) {
        return sender instanceof Player && !ItemUtils.isNullOrAir(((Player) sender).getItemInHand());
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    public static RequirementItemInHand getInstance() {
        return instance;
    }

}
