package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.ProjectileHitAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public final class ProjectileHitActionListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent projectileHitEvent) {
        final Projectile hitterProjectile = projectileHitEvent.getEntity();

        if (!(hitterProjectile.getShooter() instanceof Player player))
            return;

        final ItemStack[] playerInventoryContents = player.getInventory().getContents();

        final ItemStack projectileLaunchingItemStack;

        final UUID projectileRelatedItemUid = null; // ItemUtils.getRelatedItemUid(hitterProjectile);

        if (projectileRelatedItemUid == null)
            return;

        projectileLaunchingItemStack = Arrays.stream(playerInventoryContents)
                .filter(itemStack -> ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileHitAction)
                .filter(itemStack -> projectileRelatedItemUid.equals(ItemUtils.getUid(itemStack)))
                .findFirst()
                .orElse(null);

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
