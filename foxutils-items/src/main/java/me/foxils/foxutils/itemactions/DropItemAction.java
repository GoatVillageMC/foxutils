package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public interface DropItemAction extends ActionInterface {

    default void onDropThisItem(PlayerDropItemEvent playerDropItemEvent, ItemStack thisItemStack) {}
    default void onDropOtherItem(PlayerDropItemEvent playerDropItemEvent, ItemStack thisItemStack, ItemStack itemStackDropped) {}
}
