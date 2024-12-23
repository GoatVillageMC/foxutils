package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.TakeDamageAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class TakeDamageActionListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || !(ItemRegistry.getItemFromItemStack(item) instanceof TakeDamageAction damageActionItem))
                continue;

            damageActionItem.onTakeDamage(event, item);
        }
    }
}
