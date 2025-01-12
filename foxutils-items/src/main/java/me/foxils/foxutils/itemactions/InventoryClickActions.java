package me.foxils.foxutils.itemactions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface InventoryClickActions extends ActionInterface {

    /**
     *
     * @param inventoryClickEvent The InventoryClickEvent that called this method.
     * @param thisItemStack Item of this class that was clicked on during any InventoryClickEvent.
     */
    default void onInventoryClick(InventoryClickEvent inventoryClickEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackInCursor) {}

    /**
     *
     * @param inventoryClickEvent The InventoryClickEvent that called this method.
     * @param thisItemStack Item of this class that was in the player's cursor during any InventoryClickEvent
     */
    default void onInventoryInteract(InventoryClickEvent inventoryClickEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackClickedOn) {}
}
