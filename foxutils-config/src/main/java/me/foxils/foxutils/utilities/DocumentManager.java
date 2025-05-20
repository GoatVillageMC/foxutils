package me.foxils.foxutils.utilities;

import org.jetbrains.annotations.NotNull;

import dev.dejvokep.boostedyaml.YamlDocument;

public abstract class DocumentManager {

    private final YamlDocument document;

    public DocumentManager(final @NotNull YamlDocument documentToManage) {
        this.document = documentToManage;
    }

    protected YamlDocument getDocument() {
        return this.document;
    }
}
