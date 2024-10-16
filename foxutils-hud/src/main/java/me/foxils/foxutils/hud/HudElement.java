package me.foxils.foxutils.hud;

import me.foxils.foxutils.utilities.HudConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class HudElement {

    private final NamespacedKey hudKey;

    protected final HudConfig hudConfig;

    protected final Plugin plugin;

    public HudElement(HudConfig hudConfig, Plugin plugin) {
        this.hudConfig = hudConfig;
        this.plugin = plugin;

        this.hudKey = new NamespacedKey(plugin, hudConfig.getName());
    }

    public BaseComponent buildHudBaseComponent() {
        return new ComponentBuilder()
                .append(hudConfig.getXOffsetAsUnicodeCharacter())
                .append(hudConfig.getTextureAsUnicodeCharacter()).color(ChatColor.of("#4e5c24"))
                // Base texture pack I plan to include will contain the NoShadow shader
                // More info at https://github.com/PuckiSilver/NoShadow
                // TL:DR #4e5c24 makes the text's background shadow disappear
                .build();
    }

    public HudConfig getConfig() {
        return hudConfig;
    }

    public NamespacedKey getKey() {
        return hudKey;
    }

}
