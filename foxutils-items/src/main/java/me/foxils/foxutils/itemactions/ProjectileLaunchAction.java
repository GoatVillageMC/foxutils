package me.foxils.foxutils.itemactions;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public interface ProjectileLaunchAction extends ActionInterface {

    default void onShootProjectileWithThisItem(ProjectileLaunchEvent projectileLaunchEvent, ItemStack thisItemStack, Projectile projectileLaunched) {}
    default void onShootProjectileWithOtherItem(ProjectileLaunchEvent projectileLaunchEvent, ItemStack thisItemStack, ItemStack itemStackUsedToLaunchProjectile, Projectile projectileLaunched) {}
}
