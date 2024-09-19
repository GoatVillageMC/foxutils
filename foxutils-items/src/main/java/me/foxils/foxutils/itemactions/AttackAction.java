package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface AttackAction extends Listener {

    @EventHandler
    default void on(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) {
            return;
        }

        for (ItemStack itemUsed : attacker.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemUsed) instanceof AttackAction attackActionItem)) {
                return;
            }

            attackActionItem.attackAction(event, itemUsed);
        }
    }

    void attackAction(EntityDamageByEntityEvent event, ItemStack itemStack);
}
