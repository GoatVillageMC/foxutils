package me.foxils.foxutils.itemactions;

import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public interface TakeDamageAction extends ActionInterface {

    void onTakeDamage(EntityDamageEvent entityDamageEvent, ItemStack thisItemStack, Player playerWhoTooDamage, DamageSource damageSource);
}
