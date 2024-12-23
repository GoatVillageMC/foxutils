package me.foxils.foxutils.itemactions;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public interface KillActions extends ActionInterface {

    default void onKillWithThisItem(PlayerDeathEvent playerDeathEvent, ItemStack thisItemStack, Player killedPlayer, Player killerPlayer) {}
    default void onKillWithOtherItem(PlayerDeathEvent playerDeathEvent, ItemStack thisItemStack, ItemStack itemStackUsedToKill, Player killedPlayer, Player killerPlayer) {}
}
