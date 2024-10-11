package me.foxils.foxutils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

@SuppressWarnings("unused")
public class HudElement {

    // Y offset for all the characters needs to
    // pre-defined in the texture pack's ascent/height values

    private final String xOffsetCharacter;

    private final String textureUnicodeChracter;

    private final BaseComponent hudComponent;

    private static final String ZERO_X_OFFSET_HEX = "0xD0000";
    private static final int ZERO_X_OFFSET_INT = Integer.parseInt(ZERO_X_OFFSET_HEX.replaceFirst("0x", ""), 16);

    public HudElement(int xOffsetPixels, int textureUnicodeDecimal) {
        xOffsetCharacter = Character.toString(ZERO_X_OFFSET_INT +  xOffsetPixels);
        textureUnicodeChracter = Character.toString(textureUnicodeDecimal);

        hudComponent = new ComponentBuilder()
                .append(xOffsetCharacter)
                .append(textureUnicodeChracter).color(ChatColor.of("#4e5c24"))
                // Texture pack I plan to include contains the NoShadow shader
                // More info at https://github.com/PuckiSilver/NoShadow
                // TL:DR #4e5c24 makes the text's background shading disappear
                .build();
    }

    public BaseComponent getHudComponent() {
        return hudComponent;
    }

    public String getTextureUnicodeChracter() {
        return textureUnicodeChracter;
    }

    public String getxOffsetCharacter() {
        return xOffsetCharacter;
    }
}
