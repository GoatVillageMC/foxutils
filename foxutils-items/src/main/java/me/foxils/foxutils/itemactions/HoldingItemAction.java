package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public interface HoldingItemAction extends ActionInterface {

    int holdActionInterval = 5;

    static void holdActionCall() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            PlayerInventory inventory = player.getInventory();

            List<ItemStack> itemsHeld = Arrays.asList(
                    inventory.getItemInMainHand(),
                    inventory.getItemInOffHand());

            for (ItemStack item : itemsHeld) {
                Item customItemHeld = ItemRegistry.getItemFromItemStack(item);

                if (!(customItemHeld instanceof HoldingItemAction itemWithHoldAction)) {
                    return;
                }

                itemWithHoldAction.holdAction(player, item);
            }

        }
    }

    void holdAction(Player player, ItemStack itemStack);
}
