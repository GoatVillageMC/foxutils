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
        // Above makes sure that the item used to mine the block implements this method, and fires the onBlockMine event for it
        event.setCancelled(true);
        ItemUsed.dropItemAction(event, droppedItem);
    }

    void dropItemAction(PlayerDropItemEvent event, ItemStack itemUsed);
}
