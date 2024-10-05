package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.AttackAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class AttackActionListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) {
            return;
        }

        ItemStack itemUsedToAttack = attacker.getInventory().getItemInMainHand();

        if (!(ItemRegistry.getItemFromItemStack(itemUsedToAttack) instanceof AttackAction attackActionItem)) return;

        attackActionItem.attackAction(event, itemUsedToAttack);
    }
}