package me.foxils.foxutils.itemaction;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface InventoryClickActions extends ActionInterface {

    /**
     *
     * @param inventoryClickEvent The InventoryClickEvent that called this method.
     * @param thisItemStack Item of this class that was clicked on during any InventoryClickEvent.
     */
    default void onInventoryClick(final @NotNull InventoryClickEvent inventoryClickEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackInCursor) {
    }

    /**
     *
     * @param inventoryClickEvent The InventoryClickEvent that called this method.
     * @param thisItemStack Item of this class that was in the player's cursor during any InventoryClickEvent
     */
    default void onInventoryInteract(final @NotNull InventoryClickEvent inventoryClickEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackClickedOn) {
    }
}
