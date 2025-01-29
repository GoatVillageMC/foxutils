package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SelectItemActions extends ActionInterface {

    default void onSelectThisItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackUnselected) {}
    default void onUnselectThisItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackSelected) {}
    default void onSelectOtherItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackSelected, @Nullable ItemStack itemStackUnselected) {}
    default void onUnselectOtherItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackUnselected, @Nullable ItemStack itemStackSelected) {}
}
