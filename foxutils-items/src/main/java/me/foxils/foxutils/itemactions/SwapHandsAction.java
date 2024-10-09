package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public interface SwapHandsAction extends ActionInterface {

    void onSwapHands(PlayerSwapHandItemsEvent event, ItemStack thisItem);
}
