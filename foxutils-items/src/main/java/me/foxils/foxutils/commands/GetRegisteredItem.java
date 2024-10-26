package me.foxils.foxutils.commands;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GetRegisteredItem implements CommandExecutor {

    private final Plugin plugin;

    public GetRegisteredItem(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.DARK_RED + "No arguments provided");
            return true;
        }
        
        if (args.length == 1 && !(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "No player provided to give item");
            return true;
        }

        final Player givePlayer;

        final String itemKeyString;

        final String amountToGiveString;
        final int amountToGive;

        final Player tempPlayer = commandSender.getServer().getPlayer(args[0]);

        if (tempPlayer == null) {
            givePlayer = (Player) commandSender;
            itemKeyString = args[0];
        } else {
            givePlayer = tempPlayer;
            itemKeyString = args[1];
        }

        if (args.length == 2 && itemKeyString.equals(args[0])) {
            amountToGiveString = args[1];
        } else if (args.length >= 3) {
            amountToGiveString = args[2];
        } else {
            // I hate this
            amountToGiveString = "1";
        }

        try {
            amountToGive = Integer.parseInt(amountToGiveString);
        } catch (Exception ignored) {
            commandSender.sendMessage(amountToGiveString + ChatColor.RED + " is not a number, provide a valid item-amount");
            return true;
        }

        Item itemToCreate;

        try {
            final NamespacedKey itemKey = NamespacedKey.fromString(itemKeyString);

            final Item itemFromKey = ItemRegistry.getItemFromKey(itemKey);

            if (itemFromKey == null) {
                throw new CommandException();
            }

            itemToCreate = itemFromKey;
        } catch (Exception ignored) {
            final List<Plugin> pluginsThatDependOnItemsAPI = new ArrayList<>();

            for (Plugin loadedPlugin : plugin.getServer().getPluginManager().getPlugins()) {
                final PluginDescriptionFile descriptionFile = loadedPlugin.getDescription();
                final List<String> pluginDependencyNames = descriptionFile.getDepend();
                final List<String> pluginSoftDependencyNames = descriptionFile.getDepend();

                if (!(pluginDependencyNames.contains(plugin.getName()) || pluginSoftDependencyNames.contains(plugin.getName()))) continue;

                pluginsThatDependOnItemsAPI.add(loadedPlugin);
            }

            if (pluginsThatDependOnItemsAPI.isEmpty()) {
                commandSender.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "There are no plugins installed that depend on foxutils to create items");
                return true;
            }

            final List<Plugin> pluginsThatRegisterItems = new ArrayList<>();

            pluginsThatDependOnItemsAPI.forEach(pluginThatDependOnItemsAPI -> {
                if (!pluginThatDependOnItemsAPI.isEnabled()) return;

                pluginsThatRegisterItems.add(pluginThatDependOnItemsAPI);
            });

            if (pluginsThatRegisterItems.isEmpty()) {
                commandSender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "There are no plugins installed that successfully register items using foxutils, make sure all plugins have initialized properly");
                return true;
            }

            final List<Item> itemToCreateList = new ArrayList<>();

            final List<Plugin> pluginsThatHaveItemOfKey = new ArrayList<>();

            for (Plugin pluginThatRegistersItem : pluginsThatRegisterItems) {
                final NamespacedKey itemKey = new NamespacedKey(pluginThatRegistersItem, itemKeyString);

                final Item itemFromKey = ItemRegistry.getItemFromKey(itemKey);

                if (itemFromKey == null) {
                    continue;
                }

                itemToCreateList.add(itemFromKey);
                pluginsThatHaveItemOfKey.add(pluginThatRegistersItem);
            }

            if (itemToCreateList.isEmpty()) {
                commandSender.sendMessage(itemKeyString + ChatColor.RED + " is not a valid item-key name, provide one from /listitems");
                return true;
            }

            if (itemToCreateList.size() > 1) {
                commandSender.sendMessage(itemKeyString + ChatColor.DARK_AQUA + " is a valid item-key name in multiple plugins: ");
                pluginsThatHaveItemOfKey.forEach(pluginThatHasItemOfKey -> commandSender.sendMessage(" - " + pluginThatHasItemOfKey.getName()));
                return true;
            }

            itemToCreate = itemToCreateList.getFirst();
        }

        commandSender.sendMessage(ChatColor.DARK_GREEN + "Successfully gave " + ChatColor.RESET + amountToGive + " " + itemKeyString + ChatColor.DARK_GREEN + " to player: " + ChatColor.RESET + givePlayer.getName());
        //commandSender.sendMessage(args[0] + ChatColor.DARK_GREEN + " item is from: " + ChatColor.RESET + pluginsThatHaveItemOfKey.getFirst().getName() + ChatColor.DARK_GREEN + " plugin");

        givePlayer.getInventory().addItem(itemToCreate.createItem(amountToGive));

        return true;
    }
}
