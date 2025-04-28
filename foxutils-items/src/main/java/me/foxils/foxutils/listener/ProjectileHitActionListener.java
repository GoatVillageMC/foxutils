package me.foxils.foxutils.listener;

import java.util.UUID;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.itemaction.ProjectileHitAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utility.ItemUtils;

public final class ProjectileHitActionListener implements Listener {

    private final ItemRegistry itemRegistry;

    public ProjectileHitActionListener(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent projectileHitEvent) {
        final Projectile hitterProjectile = projectileHitEvent.getEntity();

        if (!(hitterProjectile.getShooter() instanceof final Player shooterPlayer))
            return;

        final ItemStack[] playerInventoryContents = shooterPlayer.getInventory().getContents();

        ItemStack projectileLaunchingItemStack = null;
        {
            final UUID projectileRelatedItemUid = ItemUtils.getRelatedItemUid(hitterProjectile);
            if (projectileRelatedItemUid == null && hitterProjectile instanceof final AbstractArrow abstractArrow)
                projectileLaunchingItemStack = abstractArrow.getItem();
            else {
                for (final ItemStack itemStack : playerInventoryContents) {
                    final ItemMeta itemMeta = itemStack.getItemMeta();

                    if (!(itemRegistry.getItemFromItemMeta(itemMeta) instanceof ProjectileHitAction))
                        continue;

                    if (!projectileRelatedItemUid.equals(ItemUtils.getUidFromItemMeta(itemMeta)))
                        continue;

                    projectileLaunchingItemStack = itemStack;
                    break;
                }
            }
        }

        if (projectileLaunchingItemStack == null)
            return;

        for (final ItemStack itemStack : playerInventoryContents) {
            if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final ProjectileHitAction projectileHitActionItem))
                continue;

            if (projectileLaunchingItemStack.equals(itemStack))
                projectileHitActionItem.onProjectileFromThisItemHit(projectileHitEvent, projectileLaunchingItemStack, hitterProjectile,
                        shooterPlayer);
            else
                projectileHitActionItem.onProjectileFromOtherItemHit(projectileHitEvent, itemStack, projectileLaunchingItemStack, hitterProjectile,
                        shooterPlayer);
        }
    }
}
