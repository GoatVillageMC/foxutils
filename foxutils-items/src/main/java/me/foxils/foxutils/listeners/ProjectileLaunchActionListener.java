package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.ProjectileLaunchAction;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public final class ProjectileLaunchActionListener implements Listener {

    // TODO: Finish me :(

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent projectileLaunchEvent) {
        final Projectile projectileLaunched = projectileLaunchEvent.getEntity();

        if (!(projectileLaunched.getShooter() instanceof Player playerShooter))
            return;

        final UUID projectileRelatedItemUid = ItemUtils.getRelatedItemUid(projectileLaunched);

        if (projectileRelatedItemUid == null)
            return;

        final ItemStack[] playerInventoryContents = playerShooter.getInventory().getContents();

        final ItemStack projectileLaunchingItemStack;

        projectileLaunchingItemStack = Arrays.stream(playerInventoryContents)
                .filter(itemStack -> ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileLaunchAction)
                .filter(itemStack -> projectileRelatedItemUid.equals(ItemUtils.getUid(itemStack)))
                .findFirst()
                .orElse(null);

        if (projectileLaunchingItemStack == null)
            return;

        for (ItemStack itemStack : playerInventoryContents) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof ProjectileLaunchAction projectileLaunchActionItem))
                continue;

            if (projectileLaunchingItemStack.equals(itemStack))
                projectileLaunchActionItem.onShootProjectileWithThisItem(projectileLaunchEvent, itemStack, projectileLaunched);
            else
                projectileLaunchActionItem.onShootProjectileWithOtherItem(projectileLaunchEvent, itemStack, projectileLaunchingItemStack, projectileLaunched);
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent entityShootBowEvent) {
    }
}
