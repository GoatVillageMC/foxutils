package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public interface HoldingItemAction extends ActionInterface {

    /* TODO: I want to change this to "ActiveItemAction" where if you have the item active meaning when, you have the item worn, in your hands, and some other cases, the method is called. This will be deprecating and replacing this action-interface */

    int holdActionInterval = 5;

    static void holdActionCall() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            PlayerInventory inventory = player.getInventory();

            List<ItemStack> itemsHeld = List.of(
                    inventory.getItemInMainHand(),
                    inventory.getItemInOffHand());

            for (ItemStack item : itemsHeld) {
                if (!(ItemRegistry.getItemFromItemStack(item) instanceof HoldingItemAction itemWithHoldAction))
                    continue;

                itemWithHoldAction.onHoldAction(player, item);
            }

        }
    }

    void onHoldAction(Player player, ItemStack itemStack);
}
