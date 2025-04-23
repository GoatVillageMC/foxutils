package me.foxils.foxutils.itemaction;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ClickActions extends ActionInterface {

    default void onLeftClickAir(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onShiftLeftClickAir(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onLeftClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onShiftLeftClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onRightClickAir(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onShiftRightClickAir(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onRightClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }

    default void onShiftRightClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent, final @NotNull ItemStack itemInteracted) {
    }
}
