package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public interface HotbarSelectionActions extends ActionInterface {

    default void onHotbarSelectItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItem) {}
    default void onHotbarUnselectItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItem) {}
}
