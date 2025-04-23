package me.foxils.foxutils.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import me.foxils.foxutils.itemaction.CraftItemAction;
import me.foxils.foxutils.registry.ItemRegistry;

public final class CraftItemActionListener implements Listener {

    @EventHandler
    public void onCraft(final CraftItemEvent craftItemEvent) {
        final ItemStack craftedItem = craftItemEvent.getCurrentItem();

        if (!(craftItemEvent.getWhoClicked() instanceof final Player playerWhoCrafted))
            return;

        for (final ItemStack itemStack : playerWhoCrafted.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof final CraftItemAction craftItemActionItem))
                continue;

            craftItemActionItem.onCraftOtherItem(craftItemEvent, itemStack, craftedItem, playerWhoCrafted);
        }

        if (!(ItemRegistry.getItemFromItemStack(craftedItem) instanceof final CraftItemAction craftItemActionItem))
            return;

        craftItemActionItem.onCraftThisItem(craftItemEvent, craftedItem, playerWhoCrafted);
    }
}
