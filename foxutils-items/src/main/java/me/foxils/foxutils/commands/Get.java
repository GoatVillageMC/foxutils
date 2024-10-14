package me.foxils.foxutils.commands;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Get implements CommandExecutor {

    private final Plugin plugin;

    public Get(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandName, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage("No arguments provided");
            return true;
        }
        
        if (args.length < 2 && !(commandSender instanceof Player)) {
            commandSender.sendMessage("No player provided to give item");
            return true;
        }

        final Player givePlayer;

        if (args.length == 1) {
            givePlayer = (Player) commandSender;
        } else {
            givePlayer = commandSender.getServer().getPlayer(args[1]);
        }

        if (givePlayer == null) {
            commandSender.sendMessage(args[1] + ChatColor.RED + " is not a valid player-name.");
            return true;
        }

        PlayerInventory playerInventory = givePlayer.getInventory();

        final List<Plugin> pluginsThatDependOnItemsAPI = new ArrayList<>();

        for (Plugin loadedPlugin : plugin.getServer().getPluginManager().getPlugins()) {
            final PluginDescriptionFile descriptionFile = loadedPlugin.getDescription();
            final List<String> pluginDependencyNames = descriptionFile.getDepend();
            final List<String> pluginSoftDependencyNames = descriptionFile.getDepend();

            if (!(pluginDependencyNames.contains(plugin.getName()) || pluginSoftDependencyNames.contains(plugin.getName()))) continue;

            pluginsThatDependOnItemsAPI.add(loadedPlugin);
        }

        if (pluginsThatDependOnItemsAPI.isEmpty()) {
            commandSender.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "There are no plugins installed that use foxutils to register items.");
            return true;
        }

        final List<Plugin> pluginsThatRegisterItems = new ArrayList<>();

        pluginsThatDependOnItemsAPI.forEach(pluginThatDependOnItemsAPI -> {
            if (!pluginThatDependOnItemsAPI.isEnabled()) return;

            pluginsThatRegisterItems.add(pluginThatDependOnItemsAPI);
        });

        if (pluginsThatRegisterItems.isEmpty()) {
            commandSender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "There are no plugins installed that successfully register items using foxutils, make sure all plugins have initialized properly.");
            return true;
        }

        final List<Item> itemToCreateList = new ArrayList<>();

        final List<Plugin> pluginsThatHaveItemOfKey = new ArrayList<>();

        for (Plugin pluginThatRegistersItem : pluginsThatRegisterItems) {
            final NamespacedKey itemKey = new NamespacedKey(pluginThatRegistersItem, args[0]);

            final Item itemFromKey = ItemRegistry.getItemFromKey(itemKey);

            if (itemFromKey == null) {
                continue;
            }

            itemToCreateList.add(itemFromKey);
            pluginsThatHaveItemOfKey.add(pluginThatRegistersItem);
        }

        if (itemToCreateList.isEmpty()) {
            commandSender.sendMessage(args[0] + ChatColor.RED + " is not a valid item-key.");
            return true;
        }

        if (itemToCreateList.size() > 1) {
            commandSender.sendMessage(args[0] + ChatColor.DARK_AQUA + " is a valid item-key in multiple plugins: ");
            pluginsThatHaveItemOfKey.forEach(pluginThatHasItemOfKey -> commandSender.sendMessage(" - " + pluginThatHasItemOfKey.getName()));
            return true;
        }

        int amountToGive = 1;

        if (args.length >= 2) {
            amountToGive = Integer.getInteger(args[2], 1);
        }

        commandSender.sendMessage(ChatColor.DARK_GREEN + "Successfully gave " + ChatColor.RESET + amountToGive + " " + args[0] + ChatColor.DARK_GREEN + " to player: " + ChatColor.RESET + givePlayer.getName());
        //commandSender.sendMessage(args[0] + ChatColor.DARK_GREEN + " item is from: " + ChatColor.RESET + pluginsThatHaveItemOfKey.getFirst().getName() + ChatColor.DARK_GREEN + " plugin");

        playerInventory.addItem(itemToCreateList.getFirst().createItem(amountToGive));

        return true;
    }
}
