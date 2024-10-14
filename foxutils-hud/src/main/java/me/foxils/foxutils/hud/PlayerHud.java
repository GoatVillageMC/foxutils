package me.foxils.foxutils.hud;

import me.foxils.foxutils.registry.HudRegistry;
import me.foxils.foxutils.utilities.HudConfigsContainer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerHud extends HudElement {

    private final UUID playerUUID;
    private final Plugin plugin;

    private final List<HudElement> activeHudList = new ArrayList<>();

    private static final NamespacedKey PLAYER_HUD_KEY = new NamespacedKey("foxutils-hud", "player-hud");

    public PlayerHud(@NotNull UUID playerUUID, @NotNull Plugin plugin) {
        super(HudRegistry.getHudElementFromKey(PLAYER_HUD_KEY).getConfig(), plugin);
        this.playerUUID = playerUUID;
        this.plugin = plugin;
    }

    public PlayerHud(@NotNull Player player, @NotNull Plugin plugin) {
        this(player.getUniqueId(), plugin);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public List<HudElement> getActiveHudList() {
        return List.copyOf(activeHudList);
    }

    public void addActiveHud(@NotNull HudElement hudElement) {
        activeHudList.add(hudElement);
    }

    public void removeActiveHud(@NotNull HudElement hudElement) {
        if (!activeHudList.contains(hudElement)) return;

        activeHudList.remove(hudElement);
    }

    public TextComponent buildHudComponent() {
        TextComponent hudComponent = new TextComponent(super.buildHudBaseComponent());

        activeHudList.forEach(hudElement -> hudComponent.addExtra(hudElement.buildHudBaseComponent()));

        return hudComponent;
    }

    public void sendHudToActionBar() {
        Player bukkitPlayer = plugin.getServer().getPlayer(playerUUID);

        if (bukkitPlayer == null || !bukkitPlayer.isOnline()) {
            return;
        }

        Player.Spigot spigotPlayer = bukkitPlayer.spigot();

        spigotPlayer.sendMessage(ChatMessageType.ACTION_BAR, buildHudComponent());
    }
}
