package me.foxils.foxutils.utility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

@SuppressWarnings("unused")
public class ItemAbility {

    // Move this to a properties file
    // Basically a file that I specify that will never be changed and can hold constants
    // instead of constructing a potentially infinite amount of these ItemAbility objects

    private final String name;
    private final List<String> description;

    private final ActionType actionType;

    private int cooldownTime;

    public ItemAbility(final String name, final List<String> description, final ActionType actionType) {
        this.name = name;
        this.description = description;

        this.actionType = actionType;
    }

    public ItemAbility(final String name, final List<String> description, final ActionType actionType, final int cooldownTime) {
        this.name = name;
        this.description = description;

        this.actionType = actionType;

        this.cooldownTime = cooldownTime;
    }

    public List<String> toLore() {
        final List<String> lore = new ArrayList<>();

        if (actionType == ActionType.PASSIVE) {
            lore.add(ChatColor.DARK_AQUA + actionType.getText());
        } else {
            lore.add(ChatColor.GOLD + "Ability: " + name + ChatColor.RED + ChatColor.BOLD + " [" + ChatColor.YELLOW + ChatColor.BOLD + actionType.getText() + ChatColor.RED + ChatColor.BOLD + "]");
        }

        description.forEach(line -> lore.add(ChatColor.GRAY + " " + line));

        if (cooldownTime > 0)
            lore.add(" " + ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldownTime + "s.");

        return lore;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public ActionType getType() {
        return actionType;
    }

    public int getCooldown() {
        return cooldownTime;
    }

}
