package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This interface defines a ({@link Item}) implementation that can detect when projectiles
 * associated with a source ItemStack hits entities, blocks, etc.
 * <p>
 * Detection does not happen by default when projectiles are summoned. Any summoned projectiles you want to detect
 * will need to be tagged using ({@link ItemUtils#addRelatedItemStackUid}) with the projectile and source ItemStack or
 * ItemStack UID as inputs.
 */
public interface ProjectileHitAction extends ActionInterface {

    /**
     *
     * @param projectileHitEvent The event that was used to call this method.
     * @param thisItemStack The ItemStack derived from the implementing item-class that was used to fire this method.
     * @param hitterProjectile The projectile launched and tagged using ({@link ItemUtils#addRelatedItemStackUid})
     */
    default void onProjectileFromThisItemHit(ProjectileHitEvent projectileHitEvent, ItemStack thisItemStack, Projectile hitterProjectile) {}

    /**
     *
     * @param projectileHitEvent The event that was used to call this method.
     * @param thisItemStack The ItemStack derived from the implementing item-class that was used to fire this method.
     * @param shootingItemStack The ItemStack derived from ({@link ProjectileHitAction}) that was used to shoot the projectile.
     * @param hitterProjectile The projectile launched and tagged using ({@link ItemUtils#addRelatedItemStackUid})
     */
    default void onProjectileFromOtherItemHit(ProjectileHitEvent projectileHitEvent, ItemStack thisItemStack, ItemStack shootingItemStack, Projectile hitterProjectile) {}
}
