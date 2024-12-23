package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.AttackAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class AttackActionListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker))
            return;

        final ItemStack itemUsedToAttack = attacker.getItemInUse();

        for (ItemStack itemStack : attacker.getInventory().getContents()) {
            if (itemStack == null || !(ItemRegistry.getItemFromItemStack(itemStack) instanceof AttackAction attackActionItem))
                continue;

            attackActionItem.attackAction(event, itemUsedToAttack, itemStack);
        }
    }
}