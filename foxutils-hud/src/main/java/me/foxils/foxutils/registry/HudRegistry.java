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

    private static final Map<NamespacedKey, HudConfig> KEY_HUD_CONFIG_MAP = new HashMap<>();

    public static HudConfigsContainer registerPluginHuds(Plugin plugin) {
        HudConfigsContainer hudConfigsContainer = new HudConfigsContainer(plugin);

        hudConfigsContainer.getHudConfigsSection().getKeys().forEach((hudName) -> {
            final HudConfig hudConfig = hudConfigsContainer.getHudConfig(hudName);

            registerHudConfig(hudConfig, hudConfig.getKey());
        });

        return hudConfigsContainer;
    }

    private static void registerHudConfig(HudConfig hudConfig, NamespacedKey hudKey) {
        KEY_HUD_CONFIG_MAP.put(hudKey, hudConfig);
    }

    public static HudConfig getHudConfigFromKey(NamespacedKey hudKey) {
        return KEY_HUD_CONFIG_MAP.get(hudKey);
    }

    public static Set<HudConfig> getRegisteredHudConfigs() {
        return Set.copyOf(KEY_HUD_CONFIG_MAP.values());
    }
}