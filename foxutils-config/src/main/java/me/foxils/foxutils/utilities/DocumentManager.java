package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import me.foxils.foxutils.annotations.YamlDocumentName;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class DocumentManager {

    protected final Plugin plugin;
    protected final Logger pluginLogger;

    protected final YamlDocument document;

    public DocumentManager(Plugin plugin) {
        this.plugin = plugin;
        this.pluginLogger = plugin.getLogger();

        this.document = getDocument();
    }

    private YamlDocument getDocument() {
        Class<? extends DocumentManager> clazz = getClass();

        if (!clazz.isAnnotationPresent(YamlDocumentName.class)) {
            pluginLogger.severe(clazz.getName() + " class does not specify document name with @DocumentName annotation");
            return null;
        }

        String documentName = clazz.getAnnotation(YamlDocumentName.class).documentName();

        File documentFile = new File(plugin.getDataFolder(), documentName);

        if (!documentFile.exists()) {
            plugin.saveResource(documentName, false);
        }

        try {
            return YamlDocument.create(documentFile,
                    GeneralSettings.builder()
                            .setKeyFormat(GeneralSettings.KeyFormat.STRING)
                            .build(),
                    LoaderSettings.DEFAULT,
                    DumperSettings.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
