package me.foxils.foxutils.commands;

import me.foxils.foxutils.registry.HudConfigRegistry;
import me.foxils.foxutils.utilities.HudConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ListRegisteredHuds implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (HudConfig hudConfig : HudConfigRegistry.getRegisteredHudConfigs()) {
            commandSender.sendMessage(hudConfig.getKey().toString());
        }
        return true;
    }
}
