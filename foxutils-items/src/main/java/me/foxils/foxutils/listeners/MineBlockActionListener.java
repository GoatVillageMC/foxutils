package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.MineAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MineBlockActionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || !(ItemRegistry.getItemFromItemStack(itemStack) instanceof MineAction ItemInInventory))
                continue;

            ItemInInventory.blockMineAction(event, player.getItemInUse(), itemStack);
        }
    }
}
