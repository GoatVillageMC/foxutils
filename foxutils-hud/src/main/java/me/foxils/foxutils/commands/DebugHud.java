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
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DebugHud implements CommandExecutor {

    private final Plugin plugin;

    public DebugHud(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        PlayerHud playerHud = PlayerHudRegistry.getPlayerHudFromPlayer(player, plugin);

        final HudElement fiveHud = HudRegistry.getHudElementFromKey(new NamespacedKey(plugin, "five-hud"));

        if (playerHud.getActiveHudList().contains(fiveHud)) {
            playerHud.removeActiveHud(fiveHud);
            player.sendMessage(ChatColor.RED + "Deactivated Hud Debug Mode");
            return true;
        }

        playerHud.addActiveHud(fiveHud);
        player.sendMessage(ChatColor.GREEN + "Activated Hud Debug Mode");
        return true;
    }
}
