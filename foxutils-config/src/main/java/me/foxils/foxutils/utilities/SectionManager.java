package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.block.implementation.Section;

@SuppressWarnings("unused")
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
