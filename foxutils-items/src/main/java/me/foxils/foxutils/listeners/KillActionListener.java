package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.KillAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KillActionListener implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player playerKiller)) return;

        final ItemStack itemUsed = playerKiller.getItemInHand();

        if (!(ItemRegistry.getItemFromItemStack(itemUsed) instanceof KillAction killActionItem)) return;

        killActionItem.killAction(event, itemUsed);
    }
}
