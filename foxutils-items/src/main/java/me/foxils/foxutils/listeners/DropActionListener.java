package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.DropItemAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public final class DropActionListener implements Listener {

    //https://hub.spigotmc.org/jira/browse/SPIGOT-5632
    public static final HashMap<Player, Long> DROP_INTERACT_COOLDOWN = new HashMap<>();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent playerDropItemEvent) {
        final Player player = playerDropItemEvent.getPlayer();
        DROP_INTERACT_COOLDOWN.put(player, System.currentTimeMillis() + 15);

        final ItemStack droppedItem = playerDropItemEvent.getItemDrop().getItemStack();

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof DropItemAction dropItemActionItem))
                continue;

            dropItemActionItem.onDropOtherItem(playerDropItemEvent, itemStack, droppedItem);
        }

        if (!(ItemRegistry.getItemFromItemStack(droppedItem) instanceof DropItemAction dropItemActionItem))
            return;

        dropItemActionItem.onDropThisItem(playerDropItemEvent, droppedItem);

        // Stops item from being dropped by default because why would you want that if your using it for an ability
        playerDropItemEvent.setCancelled(true);
    }

}
