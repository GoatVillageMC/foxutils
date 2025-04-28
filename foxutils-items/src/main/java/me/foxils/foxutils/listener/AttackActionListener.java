package me.foxils.foxutils.listener;

import java.util.UUID;

import org.bukkit.damage.DamageSource;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.itemaction.AttackAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utility.ItemUtils;

public final class AttackActionListener implements Listener {

    private final ItemRegistry itemRegistry;

    public AttackActionListener(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getEntity() instanceof final LivingEntity attackedEntity))
            return;

        final Player attackingPlayer;

        if (entityDamageByEntityEvent.getDamager() instanceof final Projectile projectile && projectile.getShooter() instanceof Player shooterPlayer) {
            attackingPlayer = shooterPlayer;
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
            if (!(damageSource.getDirectEntity() instanceof Projectile projectile))
                return;

            final UUID relatedItemUid = ItemUtils.getRelatedItemUid(projectile);
            if (relatedItemUid == null && projectile instanceof final AbstractArrow abstractArrow)
                itemStackUsedToAttack = abstractArrow.getWeapon();
            else {
                if (relatedItemUid == null)
                    return;

                for (final ItemStack itemStack : attackerInventoryContents) {
                    if (!(relatedItemUid.equals(ItemUtils.getUidFromItemStack(itemStack))))
                        continue;

                    itemStackUsedToAttack = itemStack;
                    break;
                }
            }
        } else
            itemStackUsedToAttack = attackerInventory.getItemInMainHand();

        for (final ItemStack itemStack : attackerInventoryContents) {
            if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final AttackAction attackActionItem))
                continue;

            if (itemStack.equals(itemStackUsedToAttack))
                attackActionItem.onAttackWithThisItem(entityDamageByEntityEvent, itemStackUsedToAttack, attackedEntity, attackingPlayer);
            else
                attackActionItem.onAttackWithOtherItem(entityDamageByEntityEvent, itemStack, itemStackUsedToAttack, attackedEntity, attackingPlayer);
        }
    }
}
