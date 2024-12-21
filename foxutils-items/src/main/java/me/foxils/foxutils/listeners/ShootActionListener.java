package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.ShootAction;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class ShootActionListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectileLaunched = event.getEntity();

        if (!(projectileLaunched.getShooter() instanceof Player playerShooter))
            return;

        PlayerInventory inventory = playerShooter.getInventory();

        List<ItemStack> itemStacksToCheck = List.of(
                inventory.getItemInOffHand(),
                inventory.getItemInMainHand()
        );

        itemStacksToCheck.forEach(itemStack -> {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof ShootAction shootActionItem))
                return;

            shootActionItem.onShootProjectile(event, projectileLaunched, itemStack);
        });
    }
}
