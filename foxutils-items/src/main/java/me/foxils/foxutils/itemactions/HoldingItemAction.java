package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HoldingItemAction extends ActionInterface {

    /* TODO: I want to change this to "ActiveItemAction" where if you have the item active meaning when, you have the item worn, in your hands, and some other cases, the method is called. This will be deprecating and replacing this action-interface */

    int holdActionInterval = 5;

    static void holdActionCall() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            final ItemStack heldItemStack = player.getItemInHand();

            if (!(ItemRegistry.getItemFromItemStack(heldItemStack) instanceof HoldingItemAction itemWithHoldAction))
                continue;

            itemWithHoldAction.onHoldAction(player, heldItemStack);
        }
    }

    void onHoldAction(Player player, ItemStack itemStack);
}
