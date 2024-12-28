package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.goatvillage.willow.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public abstract class SectionManager {

    protected final Section section;

    protected final Plugin plugin;
    protected final Logger pluginLogger;

    protected final NamespacedKey identifierKey;

    public SectionManager(Section sectionToManage, Plugin plugin) {
        this.section = sectionToManage;

        this.plugin = plugin;
        this.pluginLogger = plugin.getLogger();

        this.identifierKey = new NamespacedKey(plugin, this.getName());
    }

    public Section getSection() {
        return this.section;
    }

    public String getName() {
        return getSection().getNameAsString();
    }

    public NamespacedKey getKey() {
        return this.identifierKey;
    }

}
