package me.foxils.foxutils.utilities;

import org.bukkit.NamespacedKey;

public enum HudEnums implements HudEnum {
    PLAYER_HUD(NamespacedKey.fromString("foxutils-hud:player-hud")),
    DEBUG_HUD(NamespacedKey.fromString("foxutils-hud:five-hud"));

    private final NamespacedKey hudKey;

    HudEnums(NamespacedKey hudKey) {
        this.hudKey = hudKey;
    }

    @Override
    public NamespacedKey getHudKey() {
        return hudKey;
    }
}