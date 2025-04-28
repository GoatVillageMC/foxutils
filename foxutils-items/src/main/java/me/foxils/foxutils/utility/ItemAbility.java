package me.foxils.foxutils.utility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ItemAbility(@NotNull String name,
                          @NotNull List<String> description,
                          @NotNull ActionType actionType,
                          @Nullable Integer cooldownTime) {

    public List<String> toLore() {
        final List<String> lore = new ArrayList<>();

        if (actionType == ActionType.PASSIVE) {
            lore.add(ChatColor.DARK_AQUA + actionType.getText());
        } else {
            lore.add(
                    ChatColor.GOLD + "Ability: " + name + ChatColor.RED + ChatColor.BOLD + " [" + ChatColor.YELLOW + ChatColor.BOLD + actionType.getText() + ChatColor.RED + ChatColor.BOLD + "]");
        }

        description.forEach(line -> lore.add(ChatColor.GRAY + " " + line));

        if (cooldownTime != null && cooldownTime > 0)
            lore.add(" " + ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldownTime + "s.");

        return lore;
    }

    public enum ActionType {
        LEFT_CLICK("Left-Click"),
        SHIFT_LEFT_CLICK("Sneaking + Left-Click"),
        RIGHT_CLICK("Right-Click"),
        SHIFT_RIGHT_CLICK("Sneaking + Right-Click"),
        MIDDLE_CLICK("Middle-Click"),
        FULL_SET_BONUS("Full Set Bonus"),
        KILL("On-Kill"),
        ATTACK("On-Attack"),
        SHOT("On-Shoot"),
        LAUNCH("On-Launch"),
        MINE("On-Mine"),
        DROP("On-Drop"),
        SWAP_HANDS("Swap Off-Hand"),
        CRAFT("On-Craft"),
        SHIFT_DROP("Sneaking + On-Drop"),
        DOUBLE_JUMP("Double Jump"),
        SHIFT_DOUBLE_JUMP("Sneaking + Double Jump"),
        PASSIVE("Passive Ability:"),
        NONE("");

        private final String text;

        ActionType(final String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
