package me.foxils.foxutils.itemaction;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface KillAction extends ActionInterface {

    default void onKillWithThisItem(final @NotNull EntityDeathEvent playerDeathEvent, final @NotNull ItemStack thisItemStack, final @NotNull LivingEntity killedEntity, final @NotNull Player killerPlayer) {
    }

    default void onKillWithOtherItem(final @NotNull EntityDeathEvent playerDeathEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackUsedToKill, final @NotNull LivingEntity killedEntity, final @NotNull Player killerPlayer) {
    }
}
