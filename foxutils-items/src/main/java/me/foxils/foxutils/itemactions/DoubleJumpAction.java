package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public interface DoubleJumpAction extends ActionInterface {

    void onDoubleJump(PlayerToggleFlightEvent playerToggleFlightEvent, ItemStack thisItemStack);
}
