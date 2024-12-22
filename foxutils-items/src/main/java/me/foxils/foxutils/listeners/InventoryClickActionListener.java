package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.InventoryClickActions;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickActionListener implements Listener {

    private static final int OFFHAND_ITEM_SLOT_INDEX = 40;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player player))
            return;

        final ItemStack clickedItem;

        if (inventoryClickEvent.getAction() == InventoryAction.HOTBAR_SWAP) {
            final int eventHotbarButton = inventoryClickEvent.getHotbarButton();
            final int clickedItemIndex = (eventHotbarButton == -1) ? OFFHAND_ITEM_SLOT_INDEX : eventHotbarButton;

            /* We cannot use PlayerInventory#getItem() because it searches only in the regular inventory, like the actual storage part (the 4x9 grid.)
             Instead, we use PlayerInventory#getContents()[] because getContents returns all the items inside the full inventory (the armor, crafting grid, offhand, etc.) */
            clickedItem = player.getInventory().getContents()[clickedItemIndex];
        } else {
            clickedItem = inventoryClickEvent.getCurrentItem();
        }

        final ItemStack cursorItem = inventoryClickEvent.getCursor();

        if (clickedItem != null && ItemRegistry.getItemFromItemStack(clickedItem) instanceof InventoryClickActions inventoryClickActionItem)
            inventoryClickActionItem.onInvetoryClick(inventoryClickEvent, clickedItem, cursorItem);

        if (cursorItem != null && ItemRegistry.getItemFromItemStack(cursorItem) instanceof  InventoryClickActions inventoryClickActionItem)
            inventoryClickActionItem.onInvetoryInteract(inventoryClickEvent, cursorItem, clickedItem);
    }
}
