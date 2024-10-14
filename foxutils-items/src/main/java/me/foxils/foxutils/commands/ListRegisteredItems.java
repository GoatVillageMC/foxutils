package me.foxils.foxutils.commands;

import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListRegisteredItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ItemRegistry.getRegisteredGems().forEach(item -> commandSender.sendMessage(item.getName() + " " + ChatColor.RESET + item.getKey().getKey()));
        return true;
    }
}
