package me.foxils.foxutils.itemactions;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public interface DoubleJumpAction extends Listener, ActionInterface {

    void doubleJumpAction(PlayerToggleFlightEvent event, ItemStack item);

}
