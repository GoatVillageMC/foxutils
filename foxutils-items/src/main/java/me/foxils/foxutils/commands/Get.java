package me.foxils.foxutils.commands;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.ItemRegistry;
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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (strings.length != 2 && !(commandSender instanceof Player)) {
            commandSender.sendMessage("No player provided to give item");
            return true;
        }

        Player givePlayer;

        if (strings.length == 1) {
            givePlayer = (Player) commandSender;
        } else {
            givePlayer = commandSender.getServer().getPlayer(strings[1]);
            assert givePlayer != null;
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

        pluginsThatDependOnItemsAPI.forEach(pluginThatDependOnItemsAPI -> {
            if (pluginThatDependOnItemsAPI.isEnabled()) return;

            pluginsThatDependOnItemsAPI.remove(pluginThatDependOnItemsAPI);
        });

        final List<Plugin> pluginsThatRegisterItems = new ArrayList<>(pluginsThatDependOnItemsAPI);

        if (pluginsThatRegisterItems.isEmpty()) {
            commandSender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "There are no plugins installed that successfully register items using foxutils, make sure they have initialized properly.");
            return true;
        }

        final List<Item> itemToCreateList = new ArrayList<>();

        for (Plugin pluginThatRegistersItem : pluginsThatRegisterItems) {
            final NamespacedKey itemKey = new NamespacedKey(pluginThatRegistersItem, strings[0]);

            final Item itemFromKey = ItemRegistry.getItemFromKey(itemKey);

            if (itemFromKey == null) {
                pluginsThatRegisterItems.remove(pluginThatRegistersItem);
                continue;
            }

            itemToCreateList.add(itemFromKey);
        }

        if (itemToCreateList.isEmpty()) {
            commandSender.sendMessage(strings[0] + ChatColor.RED + "is not a valid item-key.");
            return true;
        }

        final List<Plugin> pluginsThatHaveItemOfKey = List.copyOf(pluginsThatRegisterItems);

        if (itemToCreateList.size() > 1) {
            commandSender.sendMessage(strings[0] + ChatColor.DARK_AQUA + "is a valid item-key in multiple plugins: ");
            pluginsThatHaveItemOfKey.forEach(pluginThatHasItemOfKey -> commandSender.sendMessage(" - " + pluginThatHasItemOfKey.getName()));
            return true;
        }

        int amountToGive = 1;

        if (strings.length == 3) {
            amountToGive = Integer.parseInt(strings[2]);
        }

        playerInventory.addItem(itemToCreateList.getFirst().createItem(amountToGive));

        return true;
    }
}
