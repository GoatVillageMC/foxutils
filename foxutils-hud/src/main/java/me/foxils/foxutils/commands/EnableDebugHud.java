package me.foxils.foxutils.commands;

import me.foxils.foxutils.hud.HudElement;
import me.foxils.foxutils.hud.PlayerHud;
import me.foxils.foxutils.registry.HudConfigRegistry;
import me.foxils.foxutils.registry.PlayerHudRegistry;
import me.foxils.foxutils.utilities.HudEnums;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnableDebugHud implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ChatColor.RED + "Users of this command must be players.");
            return true;
        }

        PlayerHud playerHud = PlayerHudRegistry.getPlayerHudFromPlayer(player);

        final HudElement fiveHud = new HudElement(HudConfigRegistry.getHudConfigFromEnum(HudEnums.DEBUG_HUD));

        if (playerHud.hasHudActivated(fiveHud)) {
            playerHud.removeActiveHudElement(fiveHud);
            player.sendMessage(ChatColor.RED + "Deactivated Hud Debug Mode");
            return true;
        }

        playerHud.addActiveHud(fiveHud);
        player.sendMessage(ChatColor.GREEN + "Activated Hud Debug Mode");
        return true;
    }
}
