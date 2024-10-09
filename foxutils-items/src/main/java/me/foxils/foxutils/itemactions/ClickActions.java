package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public interface ClickActions extends ActionInterface {

    default void leftClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void shiftLeftClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void leftClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void shiftLeftClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void rightClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void shiftRightClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void rightClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void shiftRightClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
}
