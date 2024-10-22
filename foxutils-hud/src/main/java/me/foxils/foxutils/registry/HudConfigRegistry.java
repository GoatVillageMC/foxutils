package me.foxils.foxutils.registry;

import me.foxils.foxutils.utilities.HudConfig;
import me.foxils.foxutils.utilities.HudConfigsContainer;
import me.foxils.foxutils.utilities.HudEnum;
import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public final class HudConfigRegistry {

    private static final Map<NamespacedKey, HudConfig> KEY_HUD_CONFIG_MAP = new HashMap<>();

    public static void registerHudConfigsOfContainer(HudConfigsContainer hudConfigsContainer) {
        for (Object hudConfigKey : hudConfigsContainer.getHudConfigsSection().getKeys()) {
            final HudConfig hudConfig = hudConfigsContainer.getHudConfig(hudConfigKey);

            registerHudConfig(hudConfig, hudConfig.getKey());
        }
    }

    private static void registerHudConfig(HudConfig hudConfig, NamespacedKey hudKey) {
        KEY_HUD_CONFIG_MAP.put(hudKey, hudConfig);
    }

    @Nullable
    public static HudConfig getHudConfigFromKey(NamespacedKey hudKey) {
        return KEY_HUD_CONFIG_MAP.get(hudKey);
    }

    @Nullable
    public static HudConfig getHudConfigFromEnum(HudEnum hudEnum) {
        return getHudConfigFromKey(hudEnum.getHudKey());
    }

    public static Set<HudConfig> getRegisteredHudConfigs() {
        return Set.copyOf(KEY_HUD_CONFIG_MAP.values());
    }
}