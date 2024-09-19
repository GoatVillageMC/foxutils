package me.foxils.foxutils.itemactions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryClickAction extends Listener {

    @EventHandler
    default void on(InventoryClickEvent event) {
        //handle it
    }
}
