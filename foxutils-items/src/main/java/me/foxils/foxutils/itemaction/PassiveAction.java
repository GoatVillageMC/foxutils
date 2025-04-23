package me.foxils.foxutils.itemaction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.registry.ItemRegistry;

public interface PassiveAction extends ActionInterface {

    int passiveTaskInterval = 5;

    static void passiveCall() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            for (final ItemStack item : player.getInventory().getContents()) {
                if (!(ItemRegistry.getItemFromItemStack(item) instanceof final PassiveAction passiveActionItem))
                    continue;

                passiveActionItem.onPassiveAction(player, item);
            }
        }
    }

    void onPassiveAction(final @NotNull Player player, final @NotNull ItemStack thisItemStack);
}
