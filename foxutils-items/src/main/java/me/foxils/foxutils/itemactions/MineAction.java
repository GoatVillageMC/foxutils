package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

// interface to show at a glance that an item has a specific funciton related to the BlockBreakEvent
// (could use a better class name)

// This is the main thing I want your opinion on
public interface MineAction extends Listener {

    @EventHandler
    default void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(item) instanceof MineAction ItemInInventory)) {
                continue;
            }

            ItemInInventory.blockMineAction(event, event.getPlayer().getItemInUse(), item);
        }
        // Above makes sure that the item in the offhand/inventory while mining the block implements this method, and fires the onBlockMine event for it, providing the item used to mine the block instead of the custom item
    }

    void blockMineAction(BlockBreakEvent event, ItemStack itemUsed, ItemStack customItem);
}
