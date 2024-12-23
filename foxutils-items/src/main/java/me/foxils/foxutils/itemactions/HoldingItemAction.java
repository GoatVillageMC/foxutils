package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public interface HoldingItemAction extends ActionInterface {

    int holdActionInterval = 5;

    static void holdActionCall() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            PlayerInventory inventory = player.getInventory();

            List<ItemStack> itemsHeld = List.of(
                    inventory.getItemInMainHand(),
                    inventory.getItemInOffHand());

            for (ItemStack item : itemsHeld) {
                if (item == null || !(ItemRegistry.getItemFromItemStack(item) instanceof HoldingItemAction itemWithHoldAction))
                    continue;

                itemWithHoldAction.holdAction(player, item);
            }

        }
    }

    void holdAction(Player player, ItemStack itemStack);
}
