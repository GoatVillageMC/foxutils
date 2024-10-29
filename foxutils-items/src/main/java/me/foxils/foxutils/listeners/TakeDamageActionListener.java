package me.foxils.foxutils.listeners;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.TakeDamageAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class TakeDamageActionListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            Item customItem = ItemRegistry.getItemFromItemStack(item);

            if (!(customItem instanceof TakeDamageAction damageActionItem)) continue;

            damageActionItem.onTakeDamage(event, item);
        }
    }
}
