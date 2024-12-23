package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.KillActions;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class KillActionListener implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent playerDeathEvent) {
        final Player killedPlayer = playerDeathEvent.getEntity();

        if (!(killedPlayer.getKiller() instanceof Player killerPlayer))
            return;

        final PlayerInventory killerInventory = killerPlayer.getInventory();
        final ItemStack[] killerInventoryContents = killerInventory.getContents();

        ItemStack killCausingItem = null;
        final DamageSource damageSource = playerDeathEvent.getDamageSource();

        if (damageSource.isIndirect()) {
            final Entity causingEntity = damageSource.getCausingEntity();

            if (causingEntity == null)
                return;

            final UUID relatedItemUid = ItemUtils.getRelatedItemUid(causingEntity);

            if (relatedItemUid == null)
                return;

            for (ItemStack itemStack : killerInventoryContents) {
                if (itemStack == null)
                    continue;

                if (!relatedItemUid.equals(ItemUtils.getUid(itemStack)))
                    continue;

                killCausingItem = itemStack;
                break;
            }
        } else {
            killCausingItem = killerInventory.getItemInMainHand();
        }

        // This... this is bad.
        if (killCausingItem == null)
            return;

        for (ItemStack itemStack : killerInventoryContents) {
            if (itemStack == null || !(ItemRegistry.getItemFromItemStack(itemStack) instanceof KillActions killActionItem))
                continue;

            if (itemStack.isSimilar(killCausingItem)) {
                killActionItem.onKillWithThisItem(playerDeathEvent, killCausingItem, killedPlayer, killerPlayer);
            } else {
                killActionItem.onKillWithOtherItem(playerDeathEvent, itemStack, killCausingItem, killedPlayer, killerPlayer);
            }

            break;
        }
    }
}
