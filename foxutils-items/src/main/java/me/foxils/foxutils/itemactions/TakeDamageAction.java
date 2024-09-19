package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public interface TakeDamageAction extends Listener {

    @EventHandler
    default void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            Item customItem = ItemRegistry.getItemFromItemStack(item);

            if (!(customItem instanceof TakeDamageAction damageActionItem)) {
                return;
            }

            damageActionItem.onTakeDamage(event);
        }
    }

    void onTakeDamage(EntityDamageEvent event);
}
