package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.ItemSelectionActions;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemSelectionActionListener implements Listener {

    @EventHandler
    public void onPlayerHoldItem(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();

        final PlayerInventory playerInventory = player.getInventory();

        final ItemStack itemStackSwappedTo = playerInventory.getItem(event.getNewSlot());
        final ItemStack itemStackSwappedFrom = playerInventory.getItem(event.getPreviousSlot());

        if (ItemRegistry.getItemFromItemStack(itemStackSwappedTo) instanceof ItemSelectionActions selectionActionItem) {
            selectionActionItem.onSelectItem(event, itemStackSwappedTo);
        }

        if (ItemRegistry.getItemFromItemStack(itemStackSwappedFrom) instanceof ItemSelectionActions selectionActionItem) {
            selectionActionItem.onUnselectItem(event, itemStackSwappedFrom);
        }
    }
}
