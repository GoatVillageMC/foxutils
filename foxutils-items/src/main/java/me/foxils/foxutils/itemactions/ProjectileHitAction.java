package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.listeners.ProjectileHitActionListener;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public interface ProjectileHitAction extends ActionInterface {

    /**
     *
     * @param projectileHitEvent The event that was used to call this method.
     * @param thisItemStack The item stack derived from the implementing item-class that was used to fire this method.
     * @param hitterProjectile The projectile launched and tagged ({@link ProjectileHitActionListener#tagLaunchedProjectile}) by this item-class that was used to fire this method.
     */
    void onProjectileHit(ProjectileHitEvent projectileHitEvent, ItemStack thisItemStack, Projectile hitterProjectile);
}
