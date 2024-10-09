package me.foxils.foxutils.itemactions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface InventoryClickAction extends ActionInterface {

    void onInvetoryPull(InventoryClickEvent event, ItemStack itemStack);
}
