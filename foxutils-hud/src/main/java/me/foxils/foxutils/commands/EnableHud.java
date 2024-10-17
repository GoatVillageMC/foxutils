package me.foxils.foxutils.commands;

import me.foxils.foxutils.hud.HudElement;
import me.foxils.foxutils.hud.PlayerHud;
import me.foxils.foxutils.registry.HudRegistry;
import me.foxils.foxutils.registry.PlayerHudRegistry;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnableHud implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.DARK_RED + "Users of this command must be a player.");
            return true;
        }

        final PlayerHud playerHud = PlayerHudRegistry.getPlayerHudFromPlayer(player);

        if (playerHud == null) {
            sender.sendMessage(ChatColor.YELLOW + "Your player-hud instance has not been initialized.");
            return true;
        }

        final NamespacedKey namespacedKey = NamespacedKey.fromString(args[0]);

        if (playerHud.hasActiveHudFromKey(namespacedKey)) {
            sender.sendMessage(ChatColor.RED + "Removing " + namespacedKey + " from player-hud instance.");
            playerHud.removeActiveHudFromKey(namespacedKey);
            return true;
        }

        playerHud.addActiveHud(new HudElement(HudRegistry.getHudConfigFromKey(namespacedKey)));
        sender.sendMessage(ChatColor.GREEN + "Adding " + namespacedKey + " to player-hud instance.");
        return true;
    }
}
