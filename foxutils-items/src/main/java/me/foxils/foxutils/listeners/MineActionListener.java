package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.MineAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MineActionListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
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
}
