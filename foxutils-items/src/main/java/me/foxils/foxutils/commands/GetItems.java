package me.foxils.foxutils.commands;

import me.foxils.foxutils.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetItems implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ItemRegistry.getRegisteredGems().forEach(item -> commandSender.sendMessage(item.getName() + " " + ChatColor.RESET + item.getKey().getKey()));
        return true;
    }
}
