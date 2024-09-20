package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.ItemRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public interface DropAction extends Listener {

    @EventHandler
    default void onItemDrop(PlayerDropItemEvent event) {
        ItemStack droppedItem = event.getItemDrop().getItemStack();

        if (!(ItemRegistry.getItemFromItemStack(droppedItem) instanceof DropAction ItemUsed)) {
            return;
        }
        // Above makes sure that the item dropped is actually an item that should fire the drop ability
        event.setCancelled(true);
        // Stops item from being dropped because why would you want that if your using it for an ability
        ItemUsed.dropItemAction(event, droppedItem);
        // Fires the method that allows the item to detect the ability
    }

    void dropItemAction(PlayerDropItemEvent event, ItemStack itemUsed);
}
