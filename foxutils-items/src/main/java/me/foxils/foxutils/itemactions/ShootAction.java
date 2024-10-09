package me.foxils.foxutils.itemactions;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public interface ShootAction extends ActionInterface {

    void onShootProjectile(ProjectileLaunchEvent event, Projectile projectile, ItemStack itemUsedToLaunch);
}
