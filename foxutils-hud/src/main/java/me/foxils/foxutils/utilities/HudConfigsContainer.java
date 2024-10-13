package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.foxils.foxutils.annotations.YamlDocumentName;
import org.bukkit.plugin.Plugin;

@YamlDocumentName(documentName = "hudConfigs.yml")
public class HudConfigsContainer extends DocumentManager {

    public HudConfigsContainer(Plugin plugin) {
        super(plugin);
    }

    public Section getConfigSectionOfHud(Object hudName) {
        return getHudConfigsSection().getSection(hudName.toString());
    }

    public Section getHudConfigsSection() {
        return document.getSection("hud-configs");
    }
}
