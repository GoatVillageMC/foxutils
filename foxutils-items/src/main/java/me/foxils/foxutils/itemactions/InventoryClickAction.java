package me.foxils.foxutils.itemactions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface InventoryClickAction {

    void onInvetoryPull(InventoryClickEvent event, ItemStack itemStack);
}
