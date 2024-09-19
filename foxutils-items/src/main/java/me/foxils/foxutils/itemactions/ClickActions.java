package me.foxils.foxutils.itemactions;

import me.foxils.foxutils.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public interface ClickActions extends Listener {

    @EventHandler
    default void onInteract(PlayerInteractEvent event) {
        ItemStack itemInteracted = event.getItem();

        if (itemInteracted == null) {
            return;
        }

        if (!(ItemRegistry.getItemFromItemStack(itemInteracted) instanceof ClickActions clickableItem)) {
            return;
        }

        Player player = event.getPlayer();

        boolean shifting = player.isSneaking();

        switch (event.getAction()) {
            case LEFT_CLICK_AIR -> {
                if (shifting) {
                    clickableItem.shiftLeftClickAir(event);
                    return;
                }
                clickableItem.leftClickAir(event);
            }
            case LEFT_CLICK_BLOCK -> {
                if (shifting) {
                    clickableItem.shiftLeftClickBlock(event);
                    return;
                }
                clickableItem.leftClickBlock(event);
            }
            case RIGHT_CLICK_AIR -> {
                if (shifting) {
                    clickableItem.shiftRightClickAir(event);
                    return;
                }
                clickableItem.rightClickAir(event);
            }
            case RIGHT_CLICK_BLOCK -> {
                if (shifting) {
                    clickableItem.shiftRightClickBlock(event);
                    return;
                }
                clickableItem.rightClickBlock(event);
            }
        }
    }

    default void leftClickAir(PlayerInteractEvent event) {}
    default void shiftLeftClickAir(PlayerInteractEvent event) {}
    default void leftClickBlock(PlayerInteractEvent event) {}
    default void shiftLeftClickBlock(PlayerInteractEvent event) {}
    default void rightClickAir(PlayerInteractEvent event) {}
    default void shiftRightClickAir(PlayerInteractEvent event) {}
    default void rightClickBlock(PlayerInteractEvent event) {}
    default void shiftRightClickBlock(PlayerInteractEvent event) {}
}
