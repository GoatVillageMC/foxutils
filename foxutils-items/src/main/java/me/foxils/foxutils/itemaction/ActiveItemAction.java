package me.foxils.foxutils.itemaction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ActiveItemAction extends ActionInterface {

    public void onActiveAction(final @NotNull Player player, final @NotNull ItemStack itemStack);
}
