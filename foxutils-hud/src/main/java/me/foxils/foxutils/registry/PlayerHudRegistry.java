package me.foxils.foxutils.registry;

import me.foxils.foxutils.hud.PlayerHud;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerHudRegistry {

    private static final Map<UUID, PlayerHud> playerHudMap = new HashMap<>();

    public static void registerPlayerHud(PlayerHud playerHud) {
        playerHudMap.put(playerHud.getPlayerUUID(), playerHud);
    }

    public static void unregisterPlayerHudFromPlayerUUID(UUID playerUUID) {
        playerHudMap.remove(playerUUID);
    }

    public static void unregisterPlayerHudFromPlayer(Player player) {
        unregisterPlayerHudFromPlayerUUID(player.getUniqueId());
    }

    public static void unregisterPlayerHud(PlayerHud playerHud) {
        unregisterPlayerHudFromPlayerUUID(playerHud.getPlayerUUID());
    }

    public static PlayerHud getPlayerHudFromPlayerUUID(UUID uuid, Plugin plugin) {
        if (!playerHudMap.containsKey(uuid)) {
            registerPlayerHud(new PlayerHud(uuid, plugin));
        }

        return playerHudMap.get(uuid);
    }

    public static PlayerHud getPlayerHudFromPlayer(Player player, Plugin plugin) {
        return getPlayerHudFromPlayerUUID(player.getUniqueId(), plugin);
    }
}