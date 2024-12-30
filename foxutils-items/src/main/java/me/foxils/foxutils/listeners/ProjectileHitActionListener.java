package me.foxils.foxutils.listeners;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemactions.ProjectileHitAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public final class ProjectileHitActionListener implements Listener {

    private final Plugin plugin;

    public ProjectileHitActionListener(Plugin plugin) {
        this.plugin = plugin;
    }

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

            if (!ItemUtils.addRelatedItemStackUid(projectileLaunched, itemStack))
                plugin.getLogger().severe("Could not tag Projectile (" + projectileLaunched + ") launched by ItemStack (" + itemStack + ")");
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent projectileHitEvent) {
        final Projectile hitterProjectile = projectileHitEvent.getEntity();

        if (!(hitterProjectile.getShooter() instanceof Player player))
            return;

        final UUID projectileRelatedItemUid;

        if (hitterProjectile instanceof Trident hitterTridentProjectile) {
            final ItemStack tridentItemStack = hitterTridentProjectile.getItem();

            if (!(ItemRegistry.getItemFromItemStack(tridentItemStack) instanceof ProjectileHitAction projectileHitActionItem))
                return;

            projectileHitActionItem.onProjectileHit(projectileHitEvent, tridentItemStack, hitterTridentProjectile);

            /* This is done because the Trident#getItem method returns a **clone** of the trident's item
               so we modify the clone, and then set it back as the tridents item. This is done in order
               to keep all custom data and modifications that may be done during ProjectileHitAction#onProjectileHit action call */
            hitterTridentProjectile.setItem(tridentItemStack);

            projectileRelatedItemUid = ItemUtils.getUid(tridentItemStack);
            /* The continuation of this method isn't an oversight and done on accident, it's purposeful.
               It allows us to fire the ProjectileHitAction#onProjectileHit in the case that the player may be in another gamemode where the trident isn't actually thrown
               firing the method with the Trident-ItemStack that is in the player's inventory. */
        } else
            projectileRelatedItemUid = ItemUtils.getRelatedItemUid(hitterProjectile);

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
