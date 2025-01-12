package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public interface SwapItemHandActions extends ActionInterface {

    default void onSwapThisItemToOffHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemStack, ItemStack itemStackSwappedToMainHand) {}
    default void onSwapThisItemToMainHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemStack, ItemStack itemStackSwappedToOffHand) {}
    default void onSwapOtherItemToMainHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemStack, ItemStack itemStackSwappedToMainHand, ItemStack itemStackSwappedToOffHand) {}
    default void onSwapOtherItemToOffHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemSTack, ItemStack itemStackSwappedToOffHand, ItemStack itemStackSwappedToMainHand) {}
}
