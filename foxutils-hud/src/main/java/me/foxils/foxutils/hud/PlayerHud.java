package me.foxils.foxutils.hud;

import me.foxils.foxutils.registry.HudConfigRegistry;
import me.foxils.foxutils.utilities.HudEnum;
import me.foxils.foxutils.utilities.HudEnums;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerHud extends HudElement {

    private final UUID playerUUID;

    private final HashSet<HudElement> activeHudSet;

    public PlayerHud(UUID playerUUID) {
        super(HudConfigRegistry.getHudConfigFromEnum(HudEnums.PLAYER_HUD));

        this.activeHudSet = new HashSet<>();
        this.playerUUID = playerUUID;
    }

    public PlayerHud(Player player) {
        this(player.getUniqueId());
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public boolean hasActiveHud() {
        return !this.activeHudSet.isEmpty();
    }

    public boolean hasHudActivatedFromKey(NamespacedKey hudKey) {
        for (HudElement hudElement : activeHudSet) {
            if (!hudElement.getKey().equals(hudKey)) continue;

            return true;
        }

        return false;
    }

    public boolean hasHudActivated(HudElement hudElement) {
        return this.hasHudActivatedFromKey(hudElement.getKey());
    }
    public boolean hasHudActivatedFromEnum(HudEnum hudEnum) {
        return this.hasHudActivatedFromKey(hudEnum.getHudKey());
    }

    @Override
    public BaseComponent buildHudBaseComponent() {
        TextComponent hudComponent = new TextComponent(super.buildHudBaseComponent());

        for (HudElement activeHudElement : activeHudSet) {
            hudComponent.addExtra(activeHudElement.buildHudBaseComponent());
        }

        return hudComponent;
    }

    @Nullable
    public HudElement getActivatedHudFromKey(@NotNull NamespacedKey hudKey) {
        for (HudElement hudElement : activeHudSet) {
            if (!hudElement.getKey().equals(hudKey)) continue;

            return hudElement;
        }

        return null;
    }

    @Nullable
    public HudElement getActivatedHudFromEnum(HudEnum hudEnum) {
        return this.getActivatedHudFromKey(hudEnum.getHudKey());
    }

    public HashSet<HudElement> getActiveHudSet() {
        return new HashSet<>(this.activeHudSet);
    }

    public void addActiveHud(HudElement hudElement) {
        this.activeHudSet.add(hudElement);
    }

    public void removeActiveHudFromKey(NamespacedKey keyToRemove) {
        this.activeHudSet.removeIf(activeHudElement -> activeHudElement.getKey().equals(keyToRemove));
    }

    public void removeActiveHudFromEnum(HudEnum hudEnum) {
        this.removeActiveHudFromKey(hudEnum.getHudKey());
    }

    public void removeActiveHudElement(HudElement hudElement) {
        this.removeActiveHudFromKey(hudElement.getKey());
    }

}
