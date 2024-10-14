package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class HudConfig {

    protected final Section hudConfigSection;
    protected final Plugin plugin;
    protected final Logger pluginLogger;

    private String xOffsetUnicodeCharacter;
    private String textureUnicodeChracter;

    private static final int ZERO_X_OFFSET_UNICODE_DECIMAL = 0xD0000;

    public HudConfig(Section hudConfigSection, Plugin plugin) {
        this.hudConfigSection = hudConfigSection;
        this.plugin = plugin;
        this.pluginLogger = plugin.getLogger();

        setXOffsetFromNumPixels(hudConfigSection.getInt("x-offset"));
        setTextureFromUnicodeCharacter(hudConfigSection.getString("texture-unicode-character"));
    }

    public Section getConfigSection() {
        return this.hudConfigSection;
    }

    public String getName() {
        return hudConfigSection.getNameAsString();
    }

    public void setTextureFromUnicodeCharacter(String textureUnicodeCharacter) {
        if (textureUnicodeCharacter == null) {
            pluginLogger.severe("Could not find texture-unicode-character for " + getName());
        }

        this.textureUnicodeChracter = textureUnicodeCharacter;
    }

    public void setTextureFromDecimal(int textureUnicodeDecimal) {
        setTextureFromUnicodeCharacter(Character.toString(textureUnicodeDecimal));
    }

    public void setXOffsetFromUnicodeCharacter(String xOffsetCharacter) {
        if (xOffsetCharacter == null) {
            pluginLogger.severe("Could not find texture-unicode-character for " + getName());
        }

        this.xOffsetUnicodeCharacter = xOffsetCharacter;
    }

    public void setXOffsetFromNumPixels(int xOffsetPixels) {
        if (xOffsetPixels > 8192 || xOffsetPixels < -8192) {
            pluginLogger.warning(getName() + " Warning: xOffsets should be no more than 8192 and no less than -8192");
        }

        setXOffsetFromUnicodeCharacter(Character.toString(ZERO_X_OFFSET_UNICODE_DECIMAL + xOffsetPixels));
    }

    public void setXOffsetFromUnicodeDecimal(int xOffsetDecimal) {
        setXOffsetFromUnicodeCharacter(Character.toString(xOffsetDecimal));
    }

    private static int getUnicodeDecimalOf(String unicodeCharacter) {
        int unicodeDecimal = 0;

        for (int i = 0; i < unicodeCharacter.length(); i++) {
            int codePoint = unicodeCharacter.codePointAt(i);

            // Skip over the second char in a surrogate pair
            if (codePoint > 0xffff) {
                i++;
            }

            unicodeDecimal = codePoint;
        }

        return unicodeDecimal;
    }

    public int getXOffsetAsInt() {
        return getUnicodeDecimalOf(xOffsetUnicodeCharacter) - ZERO_X_OFFSET_UNICODE_DECIMAL;
    }

    public int getTextureAsUnicodeDecimal() {
        return getUnicodeDecimalOf(textureUnicodeChracter);
    }

    public String getXOffsetAsUnicodeCharacter() {
        return xOffsetUnicodeCharacter;
    }

    public String getTextureAsUnicodeCharacter() {
        return textureUnicodeChracter;
    }

}

