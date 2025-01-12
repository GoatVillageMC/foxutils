package me.foxils.foxutils.itemactions;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface AttackAction extends ActionInterface {

    default void onAttackWithThisItem(EntityDamageByEntityEvent entityDamageByEntityEvent, ItemStack thisItemStack) {}
    default void onAttackWithOtherItem(EntityDamageByEntityEvent entityDamageByEntityEvent, ItemStack thisItemStack, ItemStack itemStackUsedToAttack) {}
}
