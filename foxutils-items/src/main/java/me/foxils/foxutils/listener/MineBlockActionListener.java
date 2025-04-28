package me.foxils.foxutils.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.itemaction.MineAction;
import me.foxils.foxutils.registry.ItemRegistry;

public class MineBlockActionListener implements Listener {

    private final ItemRegistry itemRegistry;

    public MineBlockActionListener(final @NotNull ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent blockBreakEvent) {
        final PlayerInventory playerInventory = blockBreakEvent.getPlayer().getInventory();

        final ItemStack itemStackUsedToMine = playerInventory.getItemInMainHand();
        for (final ItemStack itemStack : playerInventory.getContents()) {
            if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final MineAction mineActionItem))
                continue;

            if (itemStackUsedToMine.equals(itemStack))
                mineActionItem.onMineWithThisItem(blockBreakEvent, itemStackUsedToMine);
            else
                mineActionItem.onMineWithOtherItem(blockBreakEvent, itemStack, itemStackUsedToMine);
        }
    }
}
