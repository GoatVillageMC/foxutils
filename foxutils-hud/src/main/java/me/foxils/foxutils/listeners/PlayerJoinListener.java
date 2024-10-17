package me.foxils.foxutils.listeners;

import me.foxils.foxutils.hud.PlayerHud;
import me.foxils.foxutils.registry.PlayerHudRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        registerPlayerHud(event);
    }

    private void registerPlayerHud(PlayerJoinEvent playerJoinEvent) {
        PlayerHudRegistry.registerPlayerHud(new PlayerHud(playerJoinEvent.getPlayer()));
    }
}
