package me.foxils.foxutils.commands;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ListRegisteredItems implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandName, String @NotNull [] args) {
        final Collection<Item> registeredItems = ItemRegistry.getRegisteredItems();

        if (registeredItems.isEmpty()) {
            commandSender.sendMessage("There are no currently registered items");
        }

        for (final Item item : registeredItems) {
            commandSender.sendMessage(ChatColor.RED + "Name: " + ChatColor.RESET + item.getName() + ChatColor.BLUE + " Item-Key: " + ChatColor.RESET + item.getKey());
        }

        return true;
    }
}
