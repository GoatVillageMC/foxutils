package me.foxils.foxutils.utilities;

import org.jetbrains.annotations.NotNull;

import dev.dejvokep.boostedyaml.block.implementation.Section;

public abstract class SectionManager {

    protected final Section section;

    public SectionManager(final @NotNull Section sectionToManage) {
        this.section = sectionToManage;
    }

    public Section getSection() {
        return this.section;
    }

    public String getName() {
        return getSection().getNameAsString();
    }
}
