package com.github.mlefeb01.spigotutils.plugin.command;

import com.github.mlefeb01.spigotutils.api.command.AbstractCommand;
import com.github.mlefeb01.spigotutils.api.command.requirements.RequirementItemInHand;
import com.github.mlefeb01.spigotutils.plugin.ConfigYml;
import com.github.mlefeb01.spigotutils.plugin.customitem.AbstractCustomItem;
import com.github.mlefeb01.spigotutils.plugin.customitem.CustomItemRegistry;
import com.github.mlefeb01.spigotutils.plugin.customitem.upgradableitem.AbstractUpgradableItem;
import de.tr7zw.changeme.nbtapi.NBTItem;

public final class CmdUpgrade extends AbstractCommand {
    private final ConfigYml configYml;

    public CmdUpgrade(ConfigYml configYml) {
        super("upgrade");

        this.configYml = configYml;

        addRequirement(RequirementItemInHand.getInstance());

        setDescription("Opens the main upgradable item menu");
    }

    @Override
    public String getPermission() {
        return configYml.getPermissionUpgrade();
    }

    @Override
    public void execute() throws Exception {
        // At this point we already know the sender is a player who is holding an item
        final NBTItem nbtItem = new NBTItem(player.getItemInHand());
        if (!AbstractCustomItem.isCustomItem(nbtItem)) {
            player.sendMessage(configYml.getMessageNotHoldingUpgradableItem());
            return;
        }

        final AbstractCustomItem customItem = CustomItemRegistry.getInstance().get(AbstractCustomItem.getIdFromCustomItem(nbtItem));
        if (!(customItem instanceof AbstractUpgradableItem)) {
            player.sendMessage(configYml.getMessageNotHoldingUpgradableItem());
            return;
        }

        configYml.getMainUpgradeMenu().open(player);
    }

}
