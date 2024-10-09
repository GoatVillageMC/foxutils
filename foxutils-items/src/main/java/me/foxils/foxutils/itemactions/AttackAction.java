package me.foxils.foxutils.itemactions;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface AttackAction extends ActionInterface {

    void attackAction(EntityDamageByEntityEvent event, ItemStack itemStack);
}
