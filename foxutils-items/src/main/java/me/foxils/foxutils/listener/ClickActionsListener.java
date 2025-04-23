package me.foxils.foxutils.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.foxils.foxutils.itemaction.ClickActions;
import me.foxils.foxutils.registry.ItemRegistry;

public final class ClickActionsListener implements Listener {

    // TODO: Re-evaluate the switch, seems kinda long-winded imo

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (DropActionListener.DROP_INTERACT_COOLDOWN.containsKey(player) && System.currentTimeMillis() < DropActionListener.DROP_INTERACT_COOLDOWN.get(player))
            return;

        final ItemStack itemInteracted = event.getItem();

        if (!(ItemRegistry.getItemFromItemStack(itemInteracted) instanceof final ClickActions clickActionItem))
            return;

        final boolean shifting = event.getPlayer().isSneaking();
        switch (event.getAction()) {
            case LEFT_CLICK_AIR -> {
                if (shifting)
                    clickActionItem.onShiftLeftClickAir(event, itemInteracted);
                else
                    clickActionItem.onLeftClickAir(event, itemInteracted);
            }
            case LEFT_CLICK_BLOCK -> {
                if (shifting)
                    clickActionItem.onShiftLeftClickBlock(event, itemInteracted);
                else
                    clickActionItem.onLeftClickBlock(event, itemInteracted);
            }
            case RIGHT_CLICK_AIR -> {
                if (shifting)
                    clickActionItem.onShiftRightClickAir(event, itemInteracted);
                else
                    clickActionItem.onRightClickAir(event, itemInteracted);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (shifting)
                    clickActionItem.onShiftRightClickBlock(event, itemInteracted);
                else
                    clickActionItem.onRightClickBlock(event, itemInteracted);
            }
            default -> {
                return;
            }
        }
    }
}
