package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public interface ItemSelectionActions extends ActionInterface {

    default void onSelectItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItem) {}
    default void onUnselectItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItem) {}
}
