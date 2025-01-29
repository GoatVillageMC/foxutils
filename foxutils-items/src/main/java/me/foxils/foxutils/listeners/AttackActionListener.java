package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.AttackAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public final class AttackActionListener implements Listener {

    // TODO: This needs a rewrite to be in-line with the new stuff in KillActionListener (using the tagging stuff for indirect)

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player attacker))
            return;

        final ItemStack itemStackUsedToAttack = attacker.getItemInHand();

        if (itemStackUsedToAttack == null)
            return;

        if (ItemRegistry.getItemFromItemStack(itemStackUsedToAttack) instanceof AttackAction attackActionItem)
            attackActionItem.onAttackWithThisItem(entityDamageByEntityEvent, itemStackUsedToAttack);

        for (ItemStack itemStack : attacker.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof AttackAction attackActionItem))
                continue;

            if (itemStackUsedToAttack.equals(itemStack))
                return;

            attackActionItem.onAttackWithOtherItem(entityDamageByEntityEvent, itemStack, itemStackUsedToAttack);
        }
    }
}