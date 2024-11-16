package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HoldingItemAction extends ActionInterface {

    int holdActionInterval = 5;

    static void holdActionCall() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            final ItemStack playerHeldItemStack = player.getItemInHand();

            if (!(ItemRegistry.getItemFromItemStack(playerHeldItemStack) instanceof HoldingItemAction itemWithHoldAction)) return;

            itemWithHoldAction.holdAction(player, playerHeldItemStack);
        }
    }

    void holdAction(Player player, ItemStack itemStack);
}
