package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.SwapItemHandActions;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public final class SwapOffHandActionListener implements Listener {

    @EventHandler
    public void onPlayerSwapHands(PlayerSwapHandItemsEvent playerSwapHandItemsEvent) {
        final ItemStack itemStackSwappedToMainHand = playerSwapHandItemsEvent.getMainHandItem();

        if (itemStackSwappedToMainHand == null)
            return;

        final ItemStack itemStackSwappedToOffHand = playerSwapHandItemsEvent.getOffHandItem();

        if (itemStackSwappedToOffHand == null)
            return;

        for (ItemStack itemStack : playerSwapHandItemsEvent.getPlayer().getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof SwapItemHandActions swapItemHandsActionItem))
                continue;

            if (itemStackSwappedToMainHand.equals(itemStack)) {
                swapItemHandsActionItem.onSwapThisItemToMainHand(playerSwapHandItemsEvent, itemStack, itemStackSwappedToOffHand);
                swapItemHandsActionItem.onSwapOtherItemToOffHand(playerSwapHandItemsEvent, itemStack, itemStackSwappedToOffHand, itemStackSwappedToMainHand);
            } else if (itemStackSwappedToOffHand.equals(itemStack)) {
                swapItemHandsActionItem.onSwapThisItemToOffHand(playerSwapHandItemsEvent, itemStack, itemStackSwappedToMainHand);
                swapItemHandsActionItem.onSwapOtherItemToMainHand(playerSwapHandItemsEvent, itemStack, itemStackSwappedToMainHand, itemStackSwappedToOffHand);
            } else {
                swapItemHandsActionItem.onSwapOtherItemToOffHand(playerSwapHandItemsEvent, itemStack, itemStackSwappedToOffHand, itemStackSwappedToMainHand);
                swapItemHandsActionItem.onSwapOtherItemToMainHand(playerSwapHandItemsEvent, itemStack, itemStackSwappedToMainHand, itemStackSwappedToOffHand);
            }
        }
    }
}
