package me.foxils.foxutils.listeners;

import me.foxils.foxutils.hud.PlayerHud;
import me.foxils.foxutils.registry.PlayerHudRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final Plugin plugin;

    public PlayerJoinListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ScheduleSendHud scheduleSendHud = new ScheduleSendHud(player, plugin);

        scheduleSendHud.runTaskTimer(plugin, 0L, 20L);
    }

    private static class ScheduleSendHud extends BukkitRunnable {

        private final UUID playerUUID;
        private final Plugin plugin;

        public ScheduleSendHud(UUID playerUUID, Plugin plugin) {
            this.playerUUID = playerUUID;
            this.plugin = plugin;
        }

        public ScheduleSendHud(Player player, Plugin plugin) {
            this(player.getUniqueId(), plugin);
        }

        @Override
        public void run() {
            PlayerHud playerHud = PlayerHudRegistry.getPlayerHudFromPlayerUUID(playerUUID, plugin);

            playerHud.sendHudToActionBar();
        }
    }
}
