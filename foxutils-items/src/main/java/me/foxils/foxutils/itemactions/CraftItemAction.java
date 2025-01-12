package me.foxils.foxutils.itemactions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public interface CraftItemAction extends ActionInterface {

    default void onCraftThisItem(CraftItemEvent craftItemEvent, ItemStack thisItemStack, Player crafterPlayer) {}
    default void onCraftOtherItem(CraftItemEvent craftItemEvent, ItemStack thisItemStack, ItemStack craftedItemStack, Player crafterPlayer) {}
}
