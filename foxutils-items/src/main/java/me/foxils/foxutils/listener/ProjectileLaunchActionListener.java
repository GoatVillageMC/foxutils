package me.foxils.foxutils.listener;

import java.util.UUID;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.itemaction.ProjectileLaunchAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utility.ItemUtils;

public final class ProjectileLaunchActionListener implements Listener {

    private final ItemRegistry itemRegistry;

    public ProjectileLaunchActionListener(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onProjectileLaunch(final ProjectileLaunchEvent projectileLaunchEvent) {
        final Projectile launchedProjectile = projectileLaunchEvent.getEntity();

        if (!(launchedProjectile.getShooter() instanceof final Player playerShooter))
            return;

        final ItemStack[] playerInventoryContents = playerShooter.getInventory().getContents();

        ItemStack projectileLaunchingItemStack = null;

        {
            final UUID projectileRelatedItemUid = ItemUtils.getRelatedItemUid(launchedProjectile);
            if (projectileRelatedItemUid == null && launchedProjectile instanceof final AbstractArrow abstractArrow)
                projectileLaunchingItemStack = abstractArrow.getItem();
            else {
                for (final ItemStack itemStack : playerInventoryContents) {
                    final ItemMeta itemMeta = itemStack.getItemMeta();

                    if (!(itemRegistry.getItemFromItemMeta(itemMeta) instanceof ProjectileLaunchAction))
                        continue;

                    if (!projectileRelatedItemUid.equals(ItemUtils.getUid(itemMeta)))
                        continue;

                    projectileLaunchingItemStack = itemStack;
                    break;
                }
            }
        }

        if (projectileLaunchingItemStack == null)
            return;

        for (final ItemStack itemStack : playerInventoryContents) {
            if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final ProjectileLaunchAction projectileLaunchActionItem))
                continue;

            if (projectileLaunchingItemStack.equals(itemStack))
                projectileLaunchActionItem.onLaunchProjectileWithThisItem(projectileLaunchEvent, itemStack, launchedProjectile);
            else
                projectileLaunchActionItem.onLaunchProjectleWithOtherItem(projectileLaunchEvent, itemStack, projectileLaunchingItemStack, launchedProjectile);
        }
    }
}
