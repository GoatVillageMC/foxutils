package me.foxils.foxutils.itemaction;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utility.ItemUtils;

/**
 * This interface defines a ({@link Item}) implementation that can detect when projectiles
 * associated with a source ItemStack hits entities, blocks, etc.
 * <p>
 * Detection does not happen by default when projectiles are summoned. Any summoned projectiles you want to detect
 * will need to be tagged using ({@link AbstractArrow#setItem}) (where applicable, e.g. bows and tridents) or ({@link ItemUtils#addRelatedItemStackUid}) with the projectile and source ItemStack or
 * ItemStack UID as inputs. Tagging with ({@link ItemUtils#addRelatedItemStackUid}) takes priority in detection and is encouraged if you have the ability to.
 */
public interface ProjectileHitAction extends ActionInterface {

    /**
     *
     * @param projectileHitEvent The event that was used to call this method.
     * @param thisItemStack The ItemStack derived from the implementing Item-class that was used to fire this method.
     * @param hittingProjectile The projectile launched and tagged using ({@link ItemUtils#addRelatedItemStackUid})
     */
    default void onProjectileFromThisItemHit(final @NotNull ProjectileHitEvent projectileHitEvent, final @NotNull ItemStack thisItemStack, final @NotNull Projectile hittingProjectile) {
    }

    /**
     *
     * @param projectileHitEvent The event that was used to call this method.
     * @param thisItemStack The ItemStack derived from the implementing Item-class that was used to fire this method.
     * @param itemStackUsedToLaunch The ItemStack derived from ({@link ProjectileHitAction}) that was used to shoot the projectile.
     * @param hittingProjectile The projectile launched and tagged using ({@link ItemUtils#addRelatedItemStackUid})
     */
    default void onProjectileFromOtherItemHit(final @NotNull ProjectileHitEvent projectileHitEvent, final @NotNull ItemStack thisItemStack, final @NotNull ItemStack itemStackUsedToLaunch, final @NotNull Projectile hittingProjectile) {
    }
}
