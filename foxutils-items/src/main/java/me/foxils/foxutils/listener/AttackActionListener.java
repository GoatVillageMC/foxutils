package me.foxils.foxutils.listener;

import java.util.UUID;

import org.bukkit.damage.DamageSource;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.foxils.foxutils.itemaction.AttackAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utility.ItemUtils;

public final class AttackActionListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getEntity() instanceof final LivingEntity attackedEntity))
            return;

        final Player attackingPlayer;

        if (entityDamageByEntityEvent.getDamager() instanceof final AbstractArrow abstractArrow && abstractArrow.getShooter() instanceof Player) {
            attackingPlayer = (Player) abstractArrow.getShooter();
            // NOTE: itemStack is excplicitly not assigned in here to allow ItemStacks tagged using ItemUtils to take priority
        } else if (entityDamageByEntityEvent.getDamager() instanceof Player) {
            attackingPlayer = (Player) entityDamageByEntityEvent.getDamager();
        } else
            return;

        final PlayerInventory attackerInventory = attackingPlayer.getInventory();
        final ItemStack[] attackerInventoryContents = attackerInventory.getContents();

        ItemStack itemStackUsedToAttack = null;

        final DamageSource damageSource = entityDamageByEntityEvent.getDamageSource();
        if (damageSource.isIndirect()) {
            final Entity damageSourceDirectEntity = damageSource.getDirectEntity();

            if (damageSourceDirectEntity == null)
                return;

            final UUID relatedItemUid = ItemUtils.getRelatedItemUid(damageSourceDirectEntity);
            if (relatedItemUid == null && damageSourceDirectEntity instanceof final AbstractArrow abstractArrow)
                itemStackUsedToAttack = abstractArrow.getItem();
            else {
                if (relatedItemUid == null)
                    return;

                for (final ItemStack itemStack : attackerInventoryContents) {
                    if (itemStack == null || !(relatedItemUid.equals(ItemUtils.getUid(itemStack))))
                        continue;

                    itemStackUsedToAttack = itemStack;
                    break;
                }
            }
        } else
            itemStackUsedToAttack = attackerInventory.getItemInMainHand();

        for (final ItemStack itemStack : attackerInventoryContents) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof final AttackAction attackActionItem))
                continue;

            if (itemStack.equals(itemStackUsedToAttack))
                attackActionItem.onAttackWithThisItem(entityDamageByEntityEvent, itemStackUsedToAttack, attackedEntity, attackingPlayer);
            else
                attackActionItem.onAttackWithOtherItem(entityDamageByEntityEvent, itemStack, itemStackUsedToAttack, attackedEntity, attackingPlayer);
        }
    }
}
