package me.foxils.foxutils.listener;

import java.util.UUID;

import org.bukkit.damage.DamageSource;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.foxils.foxutils.itemaction.KillAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utility.ItemUtils;

@SuppressWarnings("UnstableApiUsage")
public final class KillActionListener implements Listener {

    @EventHandler
    public void onPlayerKill(final EntityDeathEvent entityDeathEvent) {
        final LivingEntity killedEntity = entityDeathEvent.getEntity();

        final Player killerPlayer = killedEntity.getKiller();

        ItemStack itemStackUsedToKill = null;

        final PlayerInventory attackerInventory = killerPlayer.getInventory();
        final ItemStack[] attackerInventoryContents = attackerInventory.getContents();

        {
            final DamageSource damageSource = entityDeathEvent.getDamageSource();
            if (damageSource.isIndirect()) {
                final Entity damageSourceDirectEntity = damageSource.getDirectEntity();

                if (damageSourceDirectEntity == null)
                    return;

                final UUID relatedItemUid = ItemUtils.getRelatedItemUid(damageSourceDirectEntity);
                if (relatedItemUid == null && damageSourceDirectEntity instanceof final AbstractArrow abstractArrow)
                    itemStackUsedToKill = abstractArrow.getItem();
                else {
                    if (relatedItemUid == null)
                        return;

                    for (final ItemStack itemStack : attackerInventoryContents) {
                        if (itemStack == null || !(relatedItemUid.equals(ItemUtils.getUid(itemStack))))
                            continue;

                        itemStackUsedToKill = itemStack;
                        break;
                    }
                }
            } else
                itemStackUsedToKill = attackerInventory.getItemInMainHand();
        }

        for (final ItemStack itemStack : attackerInventoryContents) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof final KillAction killActionItem))
                continue;

            if (itemStack.equals(itemStackUsedToKill))
                killActionItem.onKillWithThisItem(entityDeathEvent, itemStackUsedToKill, killedEntity, killerPlayer);
            else
                killActionItem.onKillWithOtherItem(entityDeathEvent, itemStack, itemStackUsedToKill, killedEntity, killerPlayer);
        }
    }
}
