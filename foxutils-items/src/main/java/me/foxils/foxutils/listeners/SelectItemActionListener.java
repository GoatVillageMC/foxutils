package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.SelectItemActions;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class SelectItemActionListener implements Listener {

    @EventHandler
    public void onPlayerHoldItem(PlayerItemHeldEvent playerItemHeldEvent) {
        final PlayerInventory playerInventory = playerItemHeldEvent.getPlayer().getInventory();

        final ItemStack itemStackSelected = playerInventory.getItem(playerItemHeldEvent.getNewSlot());
        final ItemStack itemStackUnselected = playerInventory.getItem(playerItemHeldEvent.getPreviousSlot());

        for (ItemStack itemStack : playerInventory.getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof SelectItemActions selectItemActionItem))
                continue;

            if (itemStackSelected != null && itemStackSelected.equals(itemStack)) {
                selectItemActionItem.onSelectThisItem(playerItemHeldEvent, itemStackSelected, itemStackUnselected);
                selectItemActionItem.onUnselectOtherItem(playerItemHeldEvent, itemStack, itemStackUnselected, itemStackSelected);
            } else if (itemStackUnselected != null && itemStackUnselected.equals(itemStack)) {
                selectItemActionItem.onUnselectThisItem(playerItemHeldEvent, itemStackUnselected, itemStackSelected);
                selectItemActionItem.onSelectOtherItem(playerItemHeldEvent, itemStack, itemStackSelected, itemStackUnselected);
            } else {
                selectItemActionItem.onSelectOtherItem(playerItemHeldEvent, itemStack, itemStackSelected, itemStackUnselected);
                selectItemActionItem.onUnselectOtherItem(playerItemHeldEvent, itemStack, itemStackUnselected, itemStackSelected);
            }
        }
    }
}
