package me.foxils.foxutils.itemaction;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MineAction extends ActionInterface {

    default void onMineWithThisItem(final @NotNull BlockBreakEvent blockBreakEvent,
                                    final @NotNull ItemStack thisItemStack) {
    }

    default void onMineWithOtherItem(final @NotNull BlockBreakEvent blockBreakEvent,
                                     final @NotNull ItemStack thisItemStack,
                                     final @Nullable ItemStack itemStackUsedToMine) {
    }
}
