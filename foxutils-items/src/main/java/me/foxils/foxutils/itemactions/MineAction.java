package me.foxils.foxutils.itemactions;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public interface MineAction extends ActionInterface {

    void blockMineAction(BlockBreakEvent event, ItemStack itemUsedToMine, ItemStack itemStackOfThisItem);
}
