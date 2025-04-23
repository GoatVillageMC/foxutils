package me.foxils.foxutils.itemaction;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SelectItemAction extends ActionInterface {

    default void onSelectThisItem(final @NotNull PlayerItemHeldEvent playerItemHeldEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackUnselected) {
    }

    default void onUnselectThisItem(final @NotNull PlayerItemHeldEvent playerItemHeldEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackSelected) {
    }

    default void onSelectOtherItem(final @NotNull PlayerItemHeldEvent playerItemHeldEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackSelected, final @Nullable ItemStack itemStackUnselected) {
    }

    default void onUnselectOtherItem(final @NotNull PlayerItemHeldEvent playerItemHeldEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackUnselected, final @Nullable ItemStack itemStackSelected) {
    }
}
