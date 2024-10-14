package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.DropAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DropActionListener implements Listener {

    //https://hub.spigotmc.org/jira/browse/SPIGOT-5632
    public static final HashMap<Player, Long> dropInteractCooldown = new HashMap<>();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        dropInteractCooldown.put(event.getPlayer(), System.currentTimeMillis() + 15);

        ItemStack droppedItem = event.getItemDrop().getItemStack();

        // Makes sure that the item dropped is actually an item that should fire the drop ability
        if (!(ItemRegistry.getItemFromItemStack(droppedItem) instanceof DropAction ItemUsed)) return;

        // Stops item from being dropped by default because why would you want that if your using it for an ability
        event.setCancelled(true);
        // Fires the method that allows the item to detect the ability
        ItemUsed.dropItemAction(event, droppedItem);
    }

}
