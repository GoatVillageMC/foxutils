package me.foxils.foxutils.itemaction;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.registry.ItemRegistry;

public interface HoldingItemAction extends ActionInterface {

    /* TODO: I want to change this to "ActiveItemAction" where if you have the item active meaning when, you have the item worn, in your hands, and some other cases, the method is called. This will be deprecating and replacing this action-interface */

    int holdActionInterval = 5;

    static void holdActionCall() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final PlayerInventory inventory = player.getInventory();
            for (final ItemStack item : List.of(inventory.getItemInMainHand(), inventory.getItemInOffHand())) {
                if (!(ItemRegistry.getItemFromItemStack(item) instanceof final HoldingItemAction holdingItemActionItem))
                    continue;

                holdingItemActionItem.onHoldAction(player, item);
            }

        }
    }

    void onHoldAction(final @NotNull Player player, final @NotNull ItemStack itemStack);
}
