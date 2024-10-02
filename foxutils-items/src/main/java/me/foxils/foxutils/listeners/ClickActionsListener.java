package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.ClickActions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class ClickActionsListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (DropActionListener.dropInteractCooldown.containsKey(player) && System.currentTimeMillis() < DropActionListener.dropInteractCooldown.get(player)) return;

        ItemStack itemInteracted = event.getItem();

        if (itemInteracted == null) {
            return;
        }

        if (!(ItemRegistry.getItemFromItemStack(itemInteracted) instanceof ClickActions clickableItem)) {
            return;
        }

        boolean shifting = player.isSneaking();

        switch (event.getAction()) {
            case LEFT_CLICK_AIR -> {
                if (shifting) {
                    clickableItem.shiftLeftClickAir(event, itemInteracted);
                    return;
                }
                clickableItem.leftClickAir(event, itemInteracted);
            }
            case LEFT_CLICK_BLOCK -> {
                if (shifting) {
                    clickableItem.shiftLeftClickBlock(event, itemInteracted);
                    return;
                }
                clickableItem.leftClickBlock(event, itemInteracted);
            }
            case RIGHT_CLICK_AIR -> {
                if (shifting) {
                    clickableItem.shiftRightClickAir(event, itemInteracted);
                    return;
                }
                clickableItem.rightClickAir(event, itemInteracted);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (shifting) {
                    clickableItem.shiftRightClickBlock(event, itemInteracted);
                    return;
                }
                clickableItem.rightClickBlock(event, itemInteracted);
            }
        }
    }
}
