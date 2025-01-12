package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.ProjectileHitAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public final class ProjectileHitActionListener implements Listener {

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent projectileHitEvent) {
        final Projectile hitterProjectile = projectileHitEvent.getEntity();

        if (!(hitterProjectile.getShooter() instanceof Player player))
            return;

        final ItemStack[] playerInventoryContents = player.getInventory().getContents();

        final ItemStack projectileLaunchingItemStack;

        if (hitterProjectile instanceof Trident hitterTridentProjectile && ItemRegistry.getItemFromItemStack(hitterTridentProjectile.getItem()) instanceof ProjectileHitAction projectileHitActionItem) {
            final UUID tridentRelatedItemUid = ItemUtils.getRelatedItemUid(hitterTridentProjectile);
            final ItemStack tridentItemStack = hitterTridentProjectile.getItem();

            if (tridentRelatedItemUid == null)
                return;

            if (!tridentRelatedItemUid.equals(ItemUtils.getUid(tridentItemStack))) {
                final UUID projectileRelatedItemUid = ItemUtils.getRelatedItemUid(hitterProjectile);

                if (projectileRelatedItemUid == null)
                    return;

                projectileLaunchingItemStack = Arrays.stream(playerInventoryContents)
                        .filter(itemStack -> ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileHitAction)
                        .filter(itemStack -> projectileRelatedItemUid.equals(ItemUtils.getUid(itemStack)))
                        .findFirst()
                        .orElse(null);
            } else {
                projectileHitActionItem.onProjectileFromThisItemHit(projectileHitEvent, tridentItemStack, hitterTridentProjectile);

                /* This is done because the Trident#getItem method returns a **clone** of the trident's item
                   so we modify the clone, and then set it back as the tridents item. This is done in order
                   to keep all custom data and modifications that may be done during ProjectileHitAction#onProjectileHit action call */
                hitterTridentProjectile.setItem(tridentItemStack);

                projectileLaunchingItemStack = tridentItemStack;

                /* The continuation of this method isn't an oversight and isn't done on accident, it's purposeful.
                   It allows us to fire the ProjectileHitAction#onProjectileHit in the case that the player may be in another GameMode where the trident isn't actually thrown
                   firing the method with the Trident-ItemStack that is in the player's inventory. */
            }
        } else {
            final UUID projectileRelatedItemUid = ItemUtils.getRelatedItemUid(hitterProjectile);

            if (projectileRelatedItemUid == null)
                return;

            projectileLaunchingItemStack = Arrays.stream(playerInventoryContents)
                    .filter(itemStack -> ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileHitAction)
                    .filter(itemStack -> projectileRelatedItemUid.equals(ItemUtils.getUid(itemStack)))
                    .findFirst()
                    .orElse(null);
        }

        if (projectileLaunchingItemStack == null)
            return;

        for (ItemStack itemStack : playerInventoryContents) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileHitAction projectileHitActionItem))
                continue;

            if (projectileLaunchingItemStack.equals(itemStack))
                projectileHitActionItem.onProjectileFromThisItemHit(projectileHitEvent, projectileLaunchingItemStack, hitterProjectile);
            else
                projectileHitActionItem.onProjectileFromOtherItemHit(projectileHitEvent, itemStack, projectileLaunchingItemStack, hitterProjectile);
        }
    }
}
