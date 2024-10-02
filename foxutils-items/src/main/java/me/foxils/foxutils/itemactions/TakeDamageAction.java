package me.foxils.foxutils.itemactions;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public interface TakeDamageAction {

    void onTakeDamage(EntityDamageEvent event, ItemStack itemUsedToActivate);
}
