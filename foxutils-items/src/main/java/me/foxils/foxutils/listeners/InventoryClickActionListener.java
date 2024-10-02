package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.InventoryClickAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickActionListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack itemStackClickedOn = event.getCurrentItem();

        if (!(ItemRegistry.getItemFromItemStack(itemStackClickedOn) instanceof InventoryClickAction inventoryClickActionItem)) return;

        switch (event.getAction()) {
            // will implement later as needed
        }

        inventoryClickActionItem.onInvetoryPull(event, itemStackClickedOn);
    }
}
