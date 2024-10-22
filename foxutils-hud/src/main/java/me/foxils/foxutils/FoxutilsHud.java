package me.foxils.foxutils;

import me.foxils.foxutils.commands.DebugHud;
import me.foxils.foxutils.commands.EnableHud;
import me.foxils.foxutils.commands.ListRegisteredHuds;
import me.foxils.foxutils.hud.PlayerHud;
import me.foxils.foxutils.listeners.PlayerJoinListener;
import me.foxils.foxutils.registry.HudRegistry;
import me.foxils.foxutils.registry.PlayerHudRegistry;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class FoxutilsHud extends JavaPlugin {

    private ScheduleSendHud scheduleSendHud;

    @Override
    public void onEnable() {
        // Plugin startup logic
        HudRegistry.registerPluginHudConfigs(this);

        registerCommands();
        registerEvents();
    }

    private void registerEvents() {
        scheduleSendHud = new ScheduleSendHud(this);

        scheduleSendHud.runTaskTimer(this, 0L, 20L);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("debughud")).setExecutor(new DebugHud());
        Objects.requireNonNull(getCommand("enablehud")).setExecutor(new EnableHud());
        Objects.requireNonNull(getCommand("listhuds")).setExecutor(new ListRegisteredHuds());
    }

    private static class ScheduleSendHud extends BukkitRunnable {

        private final Plugin plugin;

        public ScheduleSendHud(Plugin plugin) {
            this.plugin = plugin;
        }

        private void sendPlayerHuds() {
            final var playersOnline = plugin.getServer().getOnlinePlayers();

            for (Player player : playersOnline) {
                Player.Spigot spigotPlayer = player.spigot();

                PlayerHud playerHud = PlayerHudRegistry.getPlayerHudFromPlayer(player);

                if (playerHud == null || playerHud.hasActiveHud()) return;

                spigotPlayer.sendMessage(ChatMessageType.ACTION_BAR, playerHud.buildHudBaseComponent());
            }
        }

        @Override
        public void run() {
            sendPlayerHuds();
        }
    }

    @Override
    public void onDisable() {
        scheduleSendHud.cancel();
    }
}
