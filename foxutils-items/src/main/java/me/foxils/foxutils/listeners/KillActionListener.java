package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.KillAction;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class KillActionListener implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        LivingEntity killerEntity = event.getEntity().getKiller();

        if (!(killerEntity instanceof Player playerKiller)) {
            return;
        }

        if (event.getDamageSource().isIndirect()) {
            return;
        }

        PlayerInventory killerInventory = playerKiller.getInventory();

        final List<ItemStack> itemsUsedToKill = new ArrayList<>();

        itemsUsedToKill.add(killerInventory.getItemInMainHand());
        itemsUsedToKill.add(killerInventory.getItemInOffHand());

        itemsUsedToKill.forEach(itemUsed -> {
            if (itemUsed == null) return;
            if (itemUsed.getType().equals(Material.AIR)) return;

            if (!(ItemRegistry.getItemFromItemStack(itemUsed) instanceof KillAction killActionItem)) return;

            killActionItem.killAction(event, itemUsed);
        });
    }
}
