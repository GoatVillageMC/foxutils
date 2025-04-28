package me.foxils.foxutils.itemaction;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface DropItemAction extends ActionInterface {

    default void onDropThisItem(final @NotNull PlayerDropItemEvent playerDropItemEvent,
                                final @NotNull ItemStack thisItemStack) {
    }

    default void onDropOtherItem(final @NotNull PlayerDropItemEvent playerDropItemEvent,
                                 final @NotNull ItemStack thisItemStack,
                                 final @NotNull ItemStack itemStackDropped) {
    }
}
