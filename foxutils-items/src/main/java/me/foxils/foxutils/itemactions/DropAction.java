package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public interface DropAction extends ActionInterface {

    void dropItemAction(PlayerDropItemEvent event, ItemStack itemUsed);
}
