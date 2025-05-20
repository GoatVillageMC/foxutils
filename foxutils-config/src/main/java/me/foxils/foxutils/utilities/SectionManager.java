package me.foxils.foxutils.utilities;

import org.jetbrains.annotations.NotNull;

import dev.dejvokep.boostedyaml.block.implementation.Section;

public abstract class SectionManager {

    private final Section section;

    public SectionManager(final @NotNull Section sectionToManage) {
        this.section = sectionToManage;
    }

    protected Section getSection() {
        return this.section;
    }
}
