package me.foxils.foxutils.command;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import net.md_5.bungee.api.ChatColor;

public class GetRegisteredItem implements CommandExecutor {

    // TODO: Needs a rewrite + extensive testing

    private final Plugin plugin;

    public GetRegisteredItem(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender commandSender, final @NotNull Command command, final @NotNull String commandName, final String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.DARK_RED + "No command arguments provided.");
            return true;
        }

        Player playerToGiveTo = null;
        Item itemToGive = null;
        int amountToGive = 0;

        if (args.length == 1) {
            final ArrayList<NamespacedKey> registeredItemKeys = new ArrayList<>();

            for (final Item item : ItemRegistry.getRegisteredItems())
                registeredItemKeys.add(item.getKey());

            if (registeredItemKeys.isEmpty()) {
                commandSender.sendMessage(ChatColor.DARK_RED + "There are currently NO plugins that successfully register items using foxutils-items. Double check if your plugins have properly initialized and registered items in the console.");
                return true;
            }

            int similarItemKeys = 0;
            for (final NamespacedKey itemKey : registeredItemKeys) {
                if (!itemKey.toString().toLowerCase().contains(args[0].toLowerCase()))
                    continue;

                similarItemKeys++;
                if (similarItemKeys > 1)
                    continue;

                itemToGive = ItemRegistry.getItemFromKey(itemKey);
            }

            if (similarItemKeys > 1) {
                commandSender.sendMessage(ChatColor.RED + "There are currently " + similarItemKeys + " different Items that have a similar Item-key to '" + args[0] + ".'");
                commandSender.sendMessage(ChatColor.RED + "Specify a specific Item-key refrencing /listitems.");
                return true;
            }

            if (similarItemKeys == 0) {
                commandSender.sendMessage(ChatColor.RED + "No Item-key matching '" + args[0] + "' was found.");
                commandSender.sendMessage(ChatColor.RED + "Specify an Item-key refrencing /listitems.");
                return true;
            }

            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED + "No player was specified to give the '" + itemToGive.getName() + "' to.");
                return true;
            }

            playerToGiveTo = (Player) commandSender;
            amountToGive = 1;
        } else if (args.length == 2) {
            // Detect an Item-key first, other two second as if there is not an Item-key you cant really do anything
        }


        /*
         * playerToGiveTo?
         * itemToGive
         * amountToGive?
         */

        // TODO: Temporary
        if (playerToGiveTo == null || itemToGive == null || amountToGive == 0)
            return true;

        if (!playerToGiveTo.getInventory().addItem(itemToGive.createItem(amountToGive)).isEmpty()) {
            commandSender.sendMessage(ChatColor.YELLOW + "Could not give '" + playerToGiveTo.getName() + "' all of the neccessary Items.");
            return true;
        }

        return true;
    }
}
