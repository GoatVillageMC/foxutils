package me.foxils.foxutils.utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;

public abstract class DocumentManager {

    protected final YamlDocument document;

    private final Logger logger;

    public DocumentManager(final @NotNull File documentToManage, final @NotNull Logger logger) {
        this.document = getYamlDocument(documentToManage);
        this.logger = logger;
    }

    private YamlDocument getYamlDocument(final @NotNull File documentFile) {
        if (!documentFile.exists()) {
            try {
                if (!documentFile.createNewFile()) {
                    logger.severe("Failed to create file at path \"" + documentFile.getPath() + "\" for DocumentManager: " + this.getClass().getName());
                    return null;
                }
            } catch (final IOException ioE) {
                logger.log(Level.SEVERE, "Failed to create file at path \"" + documentFile.getPath() + "\" for DocumentManager: " + this.getClass().getName(), ioE.getStackTrace());
            }
        }

        try {
            return YamlDocument.create(documentFile, GeneralSettings.builder().setKeyFormat(GeneralSettings.KeyFormat.STRING).build(), LoaderSettings.DEFAULT, DumperSettings.DEFAULT);
        } catch (final IOException ioE) {
            throw new RuntimeException(ioE);
        }
    }
}
