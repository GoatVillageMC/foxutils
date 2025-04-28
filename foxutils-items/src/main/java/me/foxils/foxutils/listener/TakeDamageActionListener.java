package me.foxils.foxutils.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.itemaction.TakeDamageAction;
import me.foxils.foxutils.registry.ItemRegistry;

@SuppressWarnings("UnstableApiUsage")
public final class TakeDamageActionListener implements Listener {

    private final ItemRegistry itemRegistry;

    public TakeDamageActionListener(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof final Player damagedPlayer))
            return;

        for (final ItemStack itemStack : damagedPlayer.getInventory().getContents()) {
            if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final TakeDamageAction damageActionItem))
                continue;

            damageActionItem.onTakeDamage(event, itemStack, damagedPlayer);
        }
    }
}
