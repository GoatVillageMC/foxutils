package me.foxils.foxutils.itemactions;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public interface MineAction extends ActionInterface {

    default void onMineWithThisItem(BlockBreakEvent blockBreakEvent, ItemStack thisItemStack) {}
    default void onMineWithOtherItem(BlockBreakEvent blockBreakEvent, ItemStack thisItemStack, ItemStack itemStackUsedToMine) {}
}
