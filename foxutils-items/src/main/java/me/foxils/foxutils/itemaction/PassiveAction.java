package me.foxils.foxutils.itemaction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface PassiveAction extends ActionInterface {

    public void onPassiveAction(final @NotNull Player player,
                                final @NotNull ItemStack thisItemStack);
}
