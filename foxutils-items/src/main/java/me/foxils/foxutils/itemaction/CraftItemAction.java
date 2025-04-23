package me.foxils.foxutils.itemaction;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface CraftItemAction extends ActionInterface {

    default void onCraftThisItem(final @NotNull CraftItemEvent craftItemEvent, final @NotNull ItemStack thisItemStack, final @NotNull Player crafterPlayer) {
    }

    default void onCraftOtherItem(final @NotNull CraftItemEvent craftItemEvent, final @NotNull ItemStack thisItemStack, final @NotNull ItemStack craftedItemStack, final @NotNull Player crafterPlayer) {
    }
}
