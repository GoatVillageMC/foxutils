package me.foxils.foxutils.commands;

import me.foxils.foxutils.registry.HudRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetHuds implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        HudRegistry.getRegisteredHuds().forEach(hudElement -> commandSender.sendMessage(hudElement.getKey().toString()));
        return true;
    }
}
