package me.foxils.foxutils.utilities;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class HudConfig extends SectionManager {

    private String xOffsetUnicodeCharacter;
    private String textureUnicodeCharacter;

    private static final int ZERO_X_OFFSET_UNICODE_DECIMAL = 0xD0000;

    public HudConfig(Section hudConfigSection, Plugin plugin) {
        super(hudConfigSection, plugin);

        setXOffsetFromNumPixels(hudConfigSection.getInt("x-offset"));
        setTextureFromUnicodeCharacter(hudConfigSection.getString("texture-unicode-character"));
    }

    public void setTextureFromUnicodeCharacter(String textureUnicodeCharacter) {
        if (textureUnicodeCharacter == null) {
            this.pluginLogger.severe("Warning: " + this.getName() + ", Could not find texture-unicode-character.");
        }

        this.textureUnicodeCharacter = textureUnicodeCharacter;
    }

    public void setTextureFromDecimal(int textureUnicodeDecimal) {
        setTextureFromUnicodeCharacter(Character.toString(textureUnicodeDecimal));
    }

    public void setXOffsetFromUnicodeCharacter(String xOffsetCharacter) {
        if (xOffsetCharacter == null) {
            this.pluginLogger.severe("Warning: " + this.getName() + ", Could not find xOffset character.");
        }

        this.xOffsetUnicodeCharacter = xOffsetCharacter;
    }

    public void setXOffsetFromNumPixels(int xOffsetPixels) {
        if (xOffsetPixels > 8192 || xOffsetPixels < -8192) {
            this.pluginLogger.warning("Warning: " + this.getName() + ", xOffsets should be no more than 8192 and no less than -8192.");
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
        return getUnicodeDecimalOf(this.xOffsetUnicodeCharacter) - ZERO_X_OFFSET_UNICODE_DECIMAL;
    }

    public int getTextureAsUnicodeDecimal() {
        return getUnicodeDecimalOf(this.textureUnicodeCharacter);
    }
    public String getXOffsetAsUnicodeCharacter() {
        return this.xOffsetUnicodeCharacter;
    }

    public String getTextureAsUnicodeCharacter() {
        return this.textureUnicodeCharacter;
    }

}

