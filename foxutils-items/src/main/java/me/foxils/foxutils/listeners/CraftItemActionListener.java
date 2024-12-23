package me.foxils.foxutils.listeners;

import me.foxils.foxutils.itemactions.CraftItemAction;
import me.foxils.foxutils.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftItemActionListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent craftItemEvent) {
        final ItemStack craftedItem = craftItemEvent.getCurrentItem();

        final Player crafterPlayer = (Player) craftItemEvent.getWhoClicked();

        for (ItemStack itemStack : crafterPlayer.getInventory().getContents()) {
            if (itemStack == null || itemStack.equals(craftedItem))
                continue;

            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof CraftItemAction craftItemActionItem))
                continue;

            craftItemActionItem.onCraftOtherItem(craftItemEvent, craftedItem, itemStack, crafterPlayer);
        }

        if (craftedItem == null || !(ItemRegistry.getItemFromItemStack(craftedItem) instanceof CraftItemAction craftItemActionItem))
            return;

        craftItemActionItem.onCraftThisItem(craftItemEvent, craftedItem, crafterPlayer);
    }
}
