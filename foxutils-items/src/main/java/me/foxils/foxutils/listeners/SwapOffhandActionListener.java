package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.SwapOffhandAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class SwapOffhandActionListener implements Listener {

    @EventHandler
    public void onPlayerSwapHands(PlayerSwapHandItemsEvent event) {
        // TODO: Testing to see how PlayerSwapHandItemsEvent#getOffHandItem and PlayerSwapHandItemsEvent#getMainHandItem works so I can potentially fire on both items

        final ItemStack itemStackSwapped = event.getOffHandItem();

        if (!(ItemRegistry.getItemFromItemStack(itemStackSwapped) instanceof SwapOffhandAction swapHandsItem))
            return;

        swapHandsItem.onSwapOffhand(event, itemStackSwapped);

        event.setCancelled(true);
    }
}
