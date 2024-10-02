package me.foxils.foxutils.itemactions;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

// interface to show at a glance that an item has a specific funciton related to the BlockBreakEvent
// (could use a better class name)

// This is the main thing I want your opinion on
public interface MineAction {

    void blockMineAction(BlockBreakEvent event, ItemStack itemUsed, ItemStack customItem);
}
