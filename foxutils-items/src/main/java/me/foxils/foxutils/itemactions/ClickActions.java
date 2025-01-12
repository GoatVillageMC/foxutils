package me.foxils.foxutils.itemactions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface ClickActions extends ActionInterface {

    default void onLeftClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onShiftLeftClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onLeftClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onShiftLeftClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onRightClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onShiftRightClickAir(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onRightClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
    default void onShiftRightClickBlock(PlayerInteractEvent event, ItemStack itemInteracted) {}
}
