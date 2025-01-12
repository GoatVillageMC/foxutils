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

import java.util.Arrays;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public final class KillActionListener implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent playerDeathEvent) {
        final Player killedPlayer = playerDeathEvent.getEntity();

        if (!(killedPlayer.getKiller() instanceof Player killerPlayer))
            return;

        final PlayerInventory killerInventory = killerPlayer.getInventory();
        final ItemStack[] killerInventoryContents = killerInventory.getContents();

        final DamageSource damageSource = playerDeathEvent.getDamageSource();
        final ItemStack killCausingItem;

        if (damageSource.isIndirect()) {
            final Entity damageSourceDirectEntity = damageSource.getDirectEntity();

            if (damageSourceDirectEntity == null)
                return;

            final UUID relatedItemUid = ItemUtils.getRelatedItemUid(damageSourceDirectEntity);

            if (relatedItemUid == null)
                return;

            killCausingItem = Arrays.stream(killerInventoryContents)
                    .filter(itemStack -> ItemRegistry.getItemFromItemStack(itemStack) instanceof KillActions)
                    .filter(itemStack -> relatedItemUid.equals(ItemUtils.getUid(itemStack)))
                    .findFirst()
                    .orElse(null);
        } else
            killCausingItem = killerInventory.getItemInMainHand();

        if (killCausingItem == null)
            return;

        for (ItemStack itemStack : killerInventoryContents) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof KillActions killActionItem))
                continue;

            if (killCausingItem.equals(itemStack))
                killActionItem.onKillWithThisItem(playerDeathEvent, killCausingItem, killedPlayer, killerPlayer);
            else
                killActionItem.onKillWithOtherItem(playerDeathEvent, itemStack, killCausingItem, killedPlayer, killerPlayer);
        }
    }
}
