package me.foxils.foxutils.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.foxils.foxutils.itemaction.InventoryClickActions;
import me.foxils.foxutils.registry.ItemRegistry;

public final class InventoryClickActionListener implements Listener {

    private static final int OFFHAND_ITEM_SLOT_INDEX = 40;

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof final Player player))
            return;

        final ItemStack clickedItem;
        if (inventoryClickEvent.getAction() == InventoryAction.HOTBAR_SWAP) {
            final int eventHotbarButton = inventoryClickEvent.getHotbarButton();

            /* We cannot use PlayerInventory#getItem() because it searches only in the regular inventory, like the actual storage part (the 4x9 grid.)
             Instead, we use PlayerInventory#getContents()[] because getContents returns all the items inside the full inventory (the armor, crafting grid, offhand, etc.) */
            clickedItem = player.getInventory().getContents()[((eventHotbarButton == -1) ? OFFHAND_ITEM_SLOT_INDEX : eventHotbarButton)];
        } else
            clickedItem = inventoryClickEvent.getCurrentItem();

        final ItemStack cursorItem = inventoryClickEvent.getCursor();
        if (ItemRegistry.getItemFromItemStack(clickedItem) instanceof final InventoryClickActions inventoryClickActionItem)
            inventoryClickActionItem.onInventoryClick(inventoryClickEvent, clickedItem, cursorItem);
        if (ItemRegistry.getItemFromItemStack(cursorItem) instanceof final InventoryClickActions inventoryClickActionItem)
            inventoryClickActionItem.onInventoryInteract(inventoryClickEvent, cursorItem, clickedItem);
    }
}
