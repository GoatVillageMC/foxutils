package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.CraftItemAction;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public final class CraftItemActionListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent craftItemEvent) {
        final ItemStack craftedItem = craftItemEvent.getCurrentItem();

        if (!(craftItemEvent.getWhoClicked() instanceof Player playerWhoCrafted))
            return;

        for (ItemStack itemStack : playerWhoCrafted.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof CraftItemAction craftItemActionItem))
                continue;

            craftItemActionItem.onCraftOtherItem(craftItemEvent, itemStack, craftedItem, playerWhoCrafted);
        }

        if (!(ItemRegistry.getItemFromItemStack(craftedItem) instanceof CraftItemAction craftItemActionItem))
            return;

        craftItemActionItem.onCraftThisItem(craftItemEvent, craftedItem, playerWhoCrafted);
    }
}
