package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.ClickActions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class ClickActionsListener implements Listener {

    // TODO: Re-evaluate the switch, seems kinda long-winded imo

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (DropActionListener.DROP_INTERACT_COOLDOWN.containsKey(player) && System.currentTimeMillis() < DropActionListener.DROP_INTERACT_COOLDOWN.get(player))
            return;

        final ItemStack itemInteracted = event.getItem();

        if (!(ItemRegistry.getItemFromItemStack(itemInteracted) instanceof ClickActions clickActionItem))
            return;

        final boolean shifting = player.isSneaking();

        switch (event.getAction()) {
            case LEFT_CLICK_AIR -> {
                if (shifting) {
                    clickActionItem.onShiftLeftClickAir(event, itemInteracted);
                    break;
                }

                clickActionItem.onLeftClickAir(event, itemInteracted);
            }
            case LEFT_CLICK_BLOCK -> {
                if (shifting) {
                    clickActionItem.onShiftLeftClickBlock(event, itemInteracted);
                    break;
                }

                clickActionItem.onLeftClickBlock(event, itemInteracted);
            }
            case RIGHT_CLICK_AIR -> {
                if (shifting) {
                    clickActionItem.onShiftRightClickAir(event, itemInteracted);
                    break;
                }

                clickActionItem.onRightClickAir(event, itemInteracted);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (shifting) {
                    clickActionItem.onShiftRightClickBlock(event, itemInteracted);
                    break;
                }

                clickActionItem.onRightClickBlock(event, itemInteracted);
            }
        }
    }
}
