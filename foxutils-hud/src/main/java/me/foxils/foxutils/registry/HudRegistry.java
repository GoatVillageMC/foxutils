package me.foxils.foxutils.registry;

import me.foxils.foxutils.hud.HudElement;
import me.foxils.foxutils.utilities.HudConfig;
import me.foxils.foxutils.utilities.HudConfigsContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public final class HudRegistry {

    private static final Map<NamespacedKey, HudElement> keyToHudElementMap = new HashMap<>();

    public static HudConfigsContainer registerPluginHuds(Plugin plugin) {
        HudConfigsContainer hudConfigsContainer = new HudConfigsContainer(plugin);

        hudConfigsContainer.getHudConfigsSection().getKeys().forEach((hudName) -> {
            final HudConfig hudConfig = hudConfigsContainer.getHudConfig(hudName);
            final HudElement hudElement = new HudElement(hudConfig, plugin);

            registerHud(hudElement, hudElement.getKey());
        });

        return hudConfigsContainer;
    }

    private static void registerHud(HudElement hudElement, NamespacedKey hudKey) {
        keyToHudElementMap.put(hudKey, hudElement);
    }

    public static HudElement getHudElementFromKey(NamespacedKey hudKey) {
        return keyToHudElementMap.get(hudKey);
    }

    public static Set<HudElement> getRegisteredHuds() {
        return Set.copyOf(keyToHudElementMap.values());
    }
}