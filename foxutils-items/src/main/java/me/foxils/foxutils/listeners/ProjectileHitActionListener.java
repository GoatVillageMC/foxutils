package me.foxils.foxutils.listeners;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemactions.ProjectileHitAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.UUID;

public final class ProjectileHitActionListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent projectileLaunchEvent) {
        tagLaunchedProjectile(projectileLaunchEvent);
    }

    /**
     *
     * This method tags all projectiles launched by any item-class that implements {@link ProjectileHitAction}.
     * Tagging is done using the launching {@link ItemStack}'s UID created in the {@link Item#createItem} method
     *
     * @param projectileLaunchEvent The event that fired this {@link EventHandler}.
     */
    private void tagLaunchedProjectile(ProjectileLaunchEvent projectileLaunchEvent) {
        final Projectile projectileLaunched = projectileLaunchEvent.getEntity();

        if (!(projectileLaunched.getShooter() instanceof Player playerShooter))
            return;

        final PlayerInventory inventory = playerShooter.getInventory();

        for (ItemStack itemStack : List.of(inventory.getItemInOffHand(), inventory.getItemInMainHand())) {
            if (itemStack == null || !(ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileHitAction))
                continue;

            ItemUtils.addRelatedItem(projectileLaunched, itemStack);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent projectileHitEvent) {
        final Projectile hitterProjectile = projectileHitEvent.getEntity();

        if (!(hitterProjectile.getShooter() instanceof Player player))
            return;

        final UUID projectileRelatedItemUid = ItemUtils.getRelatedItemUid(hitterProjectile);

        if (projectileRelatedItemUid == null)
            return;

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || !(ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileHitAction projectileHitActionItem))
                continue;

            if (!projectileRelatedItemUid.equals(ItemUtils.getUid(itemStack)))
                continue;

            projectileHitActionItem.onProjectileHit(projectileHitEvent, itemStack, hitterProjectile);
            return;
        }
    }
}
