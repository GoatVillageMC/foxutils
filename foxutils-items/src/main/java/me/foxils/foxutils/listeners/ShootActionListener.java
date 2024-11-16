package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.ShootAction;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class ShootActionListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        final Projectile projectileLaunched = event.getEntity();

        if (!(projectileLaunched.getShooter() instanceof Player playerShooter)) return;

        final ItemStack itemStackUsedToLaunch = playerShooter.getItemInHand();

        if (!(ItemRegistry.getItemFromItemStack(itemStackUsedToLaunch) instanceof ShootAction shootActionItem)) return;

        shootActionItem.onShootProjectile(event, projectileLaunched, itemStackUsedToLaunch);
    }
}
