package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public interface SwapOffhandAction extends ActionInterface {

    void onSwapOffhand(PlayerSwapHandItemsEvent event, ItemStack thisItem);
}
