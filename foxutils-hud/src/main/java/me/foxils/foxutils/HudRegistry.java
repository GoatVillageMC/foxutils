package me.foxils.foxutils;

import me.foxils.foxutils.utilities.HudConfig;
import me.foxils.foxutils.utilities.HudConfigsContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class HudRegistry {

    private static final Map<NamespacedKey, HudElement> keyToHudElementMap = new HashMap<>();

    public static void registerPluginHuds(Plugin plugin) {
        HudConfigsContainer hudConfigsContainer = new HudConfigsContainer(plugin);

        hudConfigsContainer.getHudConfigsSection().getKeys().forEach((hudName) -> {
            final HudConfig hudConfig = new HudConfig(hudConfigsContainer.getConfigSectionOfHud(hudName), plugin);
            final HudElement hudElement = new HudElement(hudConfig, plugin);

            registerHud(hudElement, hudElement.getKey());
        });
    }

    private static void registerHud(HudElement hudElement, NamespacedKey hudKey) {
        keyToHudElementMap.put(hudKey, hudElement);
    }

    public static HudElement getHudElementFromKey(NamespacedKey hudKey) {
        return keyToHudElementMap.get(hudKey);
    }
}