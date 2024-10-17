package me.foxils.foxutils.hud;

import me.foxils.foxutils.registry.HudRegistry;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerHud extends HudElement {

    private final UUID playerUUID;

    private final List<HudElement> activeHudList = new ArrayList<>();

    private static final NamespacedKey PLAYER_HUD_KEY = new NamespacedKey("foxutils-hud", "player-hud");

    public PlayerHud(UUID playerUUID) {
        super(HudRegistry.getHudConfigFromKey(PLAYER_HUD_KEY));

        this.playerUUID = playerUUID;
    }

    public PlayerHud(Player player) {
        this(player.getUniqueId());
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public boolean hasActiveHuds() {
        return activeHudList.isEmpty();
    }

    public boolean hasActiveHud(HudElement hudElement) {
        return activeHudList.contains(hudElement);
    }

    public boolean hasActiveHudFromKey(NamespacedKey hudKey) {
        for (HudElement hudElement : activeHudList) {
            if (!hudElement.getKey().equals(hudKey)) continue;

            return true;
        }

        return false;
    }

    public List<HudElement> getActiveHudList() {
        return List.copyOf(activeHudList);
    }

    public void addActiveHud(HudElement hudElement) {
        activeHudList.add(hudElement);
    }

    public void removeActiveHudFromKey(NamespacedKey keyToRemove) {
        activeHudList.removeIf(hudElement -> hudElement.getKey().equals(keyToRemove));
    }

    public void removeActiveHud(HudElement hudElement) {
        activeHudList.remove(hudElement);
    }

    public TextComponent buildHudComponent() {
        TextComponent hudComponent = new TextComponent(super.buildHudBaseComponent());

        activeHudList.forEach(hudElement -> hudComponent.addExtra(hudElement.buildHudBaseComponent()));

        return hudComponent;
    }
}
