package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.ClickActions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class ClickActionsListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (DropActionListener.dropInteractCooldown.containsKey(player) && System.currentTimeMillis() < DropActionListener.dropInteractCooldown.get(player))
            return;

        final ItemStack itemInteracted = event.getItem();

        if (itemInteracted == null || !(ItemRegistry.getItemFromItemStack(itemInteracted) instanceof ClickActions clickableItem))
            return;

        final boolean shifting = player.isSneaking();

        switch (event.getAction()) {
            case LEFT_CLICK_AIR -> {
                if (shifting) {
                    clickableItem.shiftLeftClickAir(event, itemInteracted);
                    break;
                }

                clickableItem.leftClickAir(event, itemInteracted);
            }
            case LEFT_CLICK_BLOCK -> {
                if (shifting) {
                    clickableItem.shiftLeftClickBlock(event, itemInteracted);
                    break;
                }

                clickableItem.leftClickBlock(event, itemInteracted);
            }
            case RIGHT_CLICK_AIR -> {
                if (shifting) {
                    clickableItem.shiftRightClickAir(event, itemInteracted);
                    break;
                }

                clickableItem.rightClickAir(event, itemInteracted);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (shifting) {
                    clickableItem.shiftRightClickBlock(event, itemInteracted);
                    break;
                }

                clickableItem.rightClickBlock(event, itemInteracted);
            }
        }
    }
}
