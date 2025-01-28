package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.goatvillage.willow.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public abstract class SectionManager {

    protected final Section section;

    public SectionManager(Section sectionToManage) {
        this.section = sectionToManage;
    }

    public Section getSection() {
        return this.section;
    }

    public String getName() {
        return getSection().getNameAsString();
    }
}
