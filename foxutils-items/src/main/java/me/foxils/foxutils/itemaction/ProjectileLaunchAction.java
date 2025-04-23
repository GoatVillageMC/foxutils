package me.foxils.foxutils.itemaction;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ProjectileLaunchAction extends ActionInterface {

    default void onLaunchProjectileWithThisItem(final @NotNull ProjectileLaunchEvent projectileLaunchEvent, final @NotNull ItemStack thisItemStack, final @NotNull Projectile launchedProjectile) {
    }

    default void onLaunchProjectleWithOtherItem(final @NotNull ProjectileLaunchEvent projectileLaunchEvent, final @NotNull ItemStack thisItemStack, final @NotNull ItemStack itemStackUsedToLaunch, final Projectile launchedProjectile) {
    }
}
