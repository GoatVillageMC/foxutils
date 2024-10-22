package me.foxils.foxutils.commands;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ListRegisteredItems implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandName, String @NotNull [] args) {
        for (Item item : ItemRegistry.getRegisteredItems()) {
            commandSender.sendMessage(ChatColor.RED + "Name: " + ChatColor.RESET + item.getName() + ChatColor.RESET + " Item-Key:" + item.getKey());
        }
        return true;
    }
}
