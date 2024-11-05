package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.MineAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MineBlockActionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        final PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(item) instanceof MineAction ItemInInventory)) {
                continue;
            }

            ItemInInventory.blockMineAction(event, player.getItemInUse(), item);
        }
    }
}
