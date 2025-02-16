package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public abstract class DocumentManager {

    // TODO: Replace logging with a more general system

    protected final YamlDocument document;

    public DocumentManager(@NotNull File documentToManage) {
        this.document = getYamlDocument(documentToManage);
    }

    private YamlDocument getYamlDocument(@NotNull File documentFile) {
        if (!documentFile.exists()) {
            try {
                if (!documentFile.createNewFile()) {
                    Bukkit.getLogger().severe("Failed to create file at path \"" + documentFile.getPath()
                            + "\" for DocumentManager: " + this.getClass().getName());
                    return null;
                }
            } catch (IOException ioE) {
                Bukkit.getLogger().severe("Failed to create file at path \"" + documentFile.getPath()
                        + "\" for DocumentManager: " + this.getClass().getName());

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
