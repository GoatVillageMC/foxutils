package me.foxils.foxutils.command;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ListRegisteredItems implements CommandExecutor {

    private final ItemRegistry itemRegistry;

    public ListRegisteredItems(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command,
                             @NotNull String commandName,
                             String @NotNull [] args) {
        final Collection<Item> registeredItems = itemRegistry.getRegisteredItems();

        if (registeredItems.isEmpty()) {
            commandSender.sendMessage("There are no currently registered items");
            return true;
        }

        for (final Item item : registeredItems)
            commandSender.sendMessage(
                    ChatColor.RED + "Name: " + ChatColor.RESET + item.getName() + ChatColor.BLUE + " Item-Key: " + ChatColor.RESET + item.getKey());

        return true;
    }
}
