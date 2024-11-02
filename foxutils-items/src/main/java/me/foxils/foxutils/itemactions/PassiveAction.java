package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface PassiveAction extends ActionInterface {

    int passiveTaskInterval = 5;

    static void passiveCall() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            for (ItemStack item : player.getInventory().getContents()) {
                if (!(ItemRegistry.getItemFromItemStack(item) instanceof PassiveAction itemWithPassive)) continue;

                itemWithPassive.passiveAction(player, item);
            }

        }
    }

    void passiveAction(Player player, ItemStack ite);

}
