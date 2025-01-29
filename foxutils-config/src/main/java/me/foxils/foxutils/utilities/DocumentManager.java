package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import me.foxils.foxutils.annotations.YamlDocumentPath;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public abstract class DocumentManager {

    // TODO: Replace logging with a more general system

    protected final YamlDocument document;

    public DocumentManager() {
        this.document = getDocument();
    }

    private YamlDocument getDocument() {
        final Class<? extends DocumentManager> clazz = getClass();

        if (!clazz.isAnnotationPresent(YamlDocumentPath.class)) {
            Bukkit.getLogger().severe(clazz.getName() + " class does not specify a @YamlDocumentPath annotation");
            return null;
        }

        final String documentPath = clazz.getAnnotation(YamlDocumentPath.class).documentPath();

        if (documentPath == null) {
            Bukkit.getLogger().severe(clazz.getName() + " class does not specify a path for its document inside its @YamlDocumentPath annotation");
            return null;
        }

        final File documentFile = new File(documentPath);

        if (!documentFile.exists()) {
            try {
                if (!documentFile.createNewFile()) {
                    Bukkit.getLogger().severe("Failed to create file at path \"" + documentPath + "\" for DocumentManager: " + clazz.getName());
                    return null;
                }
            } catch (IOException ioE) {
                Bukkit.getLogger().severe("Failed to create file at path \"" + documentPath + "\" for DocumentManager: " + clazz.getName());

                for (StackTraceElement stackTraceElement : ioE.getStackTrace())
                    Bukkit.getLogger().severe(stackTraceElement.toString());
            }
        }

        try {
            return YamlDocument.create(documentFile,
                    GeneralSettings.builder()
                            .setKeyFormat(GeneralSettings.KeyFormat.STRING)
                            .build(),
                    LoaderSettings.DEFAULT,
                    DumperSettings.DEFAULT);
        } catch (IOException ioE) {
            throw new RuntimeException(ioE);
        }
    }
}