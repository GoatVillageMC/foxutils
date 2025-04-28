package me.foxils.foxutils.listener;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.itemaction.DropItemAction;
import me.foxils.foxutils.registry.ItemRegistry;

public final class DropActionListener implements Listener {

    // NOTE: https://hub.spigotmc.org/jira/browse/SPIGOT-5632
    static final HashMap<Player, Long> DROP_INTERACT_COOLDOWN = new HashMap<>();

    private final ItemRegistry itemRegistry;

    public DropActionListener(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent playerDropItemEvent) {
        final Player player = playerDropItemEvent.getPlayer();
        DROP_INTERACT_COOLDOWN.put(player, System.currentTimeMillis() + 15);

        final ItemStack droppedItem = playerDropItemEvent.getItemDrop().getItemStack();

        for (final ItemStack itemStack : player.getInventory().getContents()) {
            if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final DropItemAction dropItemActionItem))
                continue;

            dropItemActionItem.onDropOtherItem(playerDropItemEvent, itemStack, droppedItem);
        }

        if (!(itemRegistry.getItemFromItemStack(droppedItem) instanceof final DropItemAction dropItemActionItem))
            return;

        dropItemActionItem.onDropThisItem(playerDropItemEvent, droppedItem);
        playerDropItemEvent.setCancelled(true);
    }
}
