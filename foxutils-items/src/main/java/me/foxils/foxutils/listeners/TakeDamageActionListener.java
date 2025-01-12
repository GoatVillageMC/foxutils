package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.TakeDamageAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public final class TakeDamageActionListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player damagedPlayer))
            return;

        for (ItemStack itemStack : damagedPlayer.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof TakeDamageAction damageActionItem))
                continue;

            damageActionItem.onTakeDamage(event, itemStack, damagedPlayer, event.getDamageSource());
        }
    }
}
