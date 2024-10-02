package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public interface DropAction {

    void dropItemAction(PlayerDropItemEvent event, ItemStack itemUsed);
}
