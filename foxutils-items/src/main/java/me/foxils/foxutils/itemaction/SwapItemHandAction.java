package me.foxils.foxutils.itemaction;

import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SwapItemHandAction extends ActionInterface {

    default void onSwapThisItemToOffHand(final @NotNull PlayerSwapHandItemsEvent playerSwapHandItemsEvent,
                                         final @NotNull ItemStack thisItemStack,
                                         final @Nullable ItemStack itemStackSwappedToMainHand) {
    }

    default void onSwapThisItemToMainHand(final @NotNull PlayerSwapHandItemsEvent playerSwapHandItemsEvent,
                                          final @NotNull ItemStack thisItemStack,
                                          final @Nullable ItemStack itemStackSwappedToOffHand) {
    }

    default void onSwapOtherItemToMainHand(final @NotNull PlayerSwapHandItemsEvent playerSwapHandItemsEvent,
                                           final @NotNull ItemStack thisItemStack,
                                           final @NotNull ItemStack itemStackSwappedToMainHand,
                                           final @Nullable ItemStack itemStackSwappedToOffHand) {
    }

    default void onSwapOtherItemToOffHand(final @NotNull PlayerSwapHandItemsEvent playerSwapHandItemsEvent,
                                          final @NotNull ItemStack thisItemSTack,
                                          final @NotNull ItemStack itemStackSwappedToOffHand,
                                          final @Nullable ItemStack itemStackSwappedToMainHand) {
    }
}
