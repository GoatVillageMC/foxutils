package me.foxils.foxutils.itemaction;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AttackAction extends ActionInterface {

    default void onAttackWithThisItem(final @NotNull EntityDamageByEntityEvent entityDamageByEntityEvent, final @NotNull ItemStack thisItemStack, final @NotNull LivingEntity attackedEntity, final @NotNull Player attackingPlayer) {
    }

    default void onAttackWithOtherItem(final @NotNull EntityDamageByEntityEvent entityDamageByEntityEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackUsedToAttack, final @NotNull LivingEntity attackedEntity, final @NotNull Player attackingPlayer) {
    }
}
