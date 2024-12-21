package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.InventoryClickActions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickActionListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player))
            return;

        final ItemStack clickedItem = inventoryClickEvent.getCurrentItem();
        final ItemStack cursorItem = inventoryClickEvent.getCursor();

        if (clickedItem != null && ItemRegistry.getItemFromItemStack(clickedItem) instanceof InventoryClickActions inventoryClickActionItem)
            inventoryClickActionItem.onInvetoryClick(inventoryClickEvent, clickedItem, cursorItem);

        if (cursorItem != null && ItemRegistry.getItemFromItemStack(cursorItem) instanceof  InventoryClickActions inventoryClickActionItem)
            inventoryClickActionItem.onInvetoryInteract(inventoryClickEvent, cursorItem, clickedItem);
    }
}
