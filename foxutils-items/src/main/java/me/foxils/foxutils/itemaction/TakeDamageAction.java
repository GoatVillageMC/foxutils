package me.foxils.foxutils.itemaction;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface TakeDamageAction extends ActionInterface {

    public void onTakeDamage(final @NotNull EntityDamageEvent entityDamageEvent,
                             final @NotNull ItemStack thisItemStack,
                             final @NotNull Player damagedPlayer);
}
