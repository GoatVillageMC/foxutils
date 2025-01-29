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
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        final Player player = blockBreakEvent.getPlayer();

        final PlayerInventory playerInventory = player.getInventory();

        final ItemStack itemStackUsedToMine = playerInventory.getItemInHand();

        for (ItemStack itemStack : playerInventory.getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof MineAction mineActionItem))
                continue;

            if (itemStackUsedToMine.equals(itemStack))
                mineActionItem.onMineWithThisItem(blockBreakEvent, itemStackUsedToMine);
            else
                mineActionItem.onMineWithOtherItem(blockBreakEvent, itemStack, itemStackUsedToMine);
        }
    }
}
