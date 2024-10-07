package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.SwapHandsAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class SwapHandsActionListener implements Listener {

    @EventHandler
    public void onPlayerSwapHands(PlayerSwapHandItemsEvent event) {
        ItemStack itemStackSwapped = event.getOffHandItem();

        if (!(ItemRegistry.getItemFromItemStack(itemStackSwapped) instanceof SwapHandsAction swapHandsItem)) return;

        swapHandsItem.onSwapHands(event, itemStackSwapped);

        event.setCancelled(true);
    }
}
