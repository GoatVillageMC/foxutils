package me.foxils.foxutils.items;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemactions.CraftItemAction;
import me.foxils.foxutils.itemactions.KillActions;
import me.foxils.foxutils.itemactions.ProjectileHitAction;
import me.foxils.foxutils.utilities.ItemAbility;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("all")
public class ThyTestItem extends Item implements CraftItemAction, KillActions, ProjectileHitAction {

    private final NamespacedKey TRIDENT_TEST_COOLDOWN;

    public ThyTestItem(@NotNull Plugin plugin, @NotNull Material itemMaterial, @NotNull String name, int customModelData, @Nullable List<ItemAbility> abilityList, @Nullable List<ItemStack> itemsForRecipe, boolean isRecipeShaped) {
        super(plugin, itemMaterial, name, customModelData, abilityList, itemsForRecipe, isRecipeShaped);

        this.TRIDENT_TEST_COOLDOWN = new NamespacedKey(plugin, "trident_test_cooldown");
    }

    @Override
    public @NotNull ItemStack createItem(int amount) {
        final ItemStack newItem = super.createItem(amount);
        final ItemMeta itemMeta = newItem.getItemMeta();
        assert itemMeta != null;

        itemMeta.addEnchant(Enchantment.LOYALTY, 3, false);

        newItem.setItemMeta(itemMeta);
        return newItem;
    }

    @Override
    public void onCraftThisItem(CraftItemEvent craftItemEvent, ItemStack thisItemStack, Player crafterPlayer) {
        crafterPlayer.sendMessage("You have crafted a test item somehow tf");
    }

    @Override
    public void onCraftOtherItem(CraftItemEvent craftItemEvent, ItemStack craftedItemStack, ItemStack thisItemStack, Player crafterPlayer) {
        crafterPlayer.sendMessage("You have crafted another item and the test item detected it");
    }

    @Override
    public void onKillWithThisItem(PlayerDeathEvent playerDeathEvent, ItemStack thisItemStack, Player killedPlayer, Player killerPlayer) {
        killerPlayer.sendMessage("You killed with the test item.");
    }

    @Override
    public void onKillWithOtherItem(PlayerDeathEvent playerDeathEvent, ItemStack thisItemStack, ItemStack itemStackUsedToKill, Player killedPlayer, Player killerPlayer) {
        killerPlayer.sendMessage("You killed with an item and the test item detected it");
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent projectileHitEvent, ItemStack thisItemStack, Projectile hitterProjectile) {
        tridentCooldownTest(thisItemStack, hitterProjectile);
    }

    private void tridentCooldownTest(ItemStack thisItemStack, Projectile hitterProjectile) {
        if (ItemUtils.getCooldown(TRIDENT_TEST_COOLDOWN, thisItemStack, 10L))
            ((Player) hitterProjectile.getShooter()).sendMessage("The trident has a cooldown bum");
        else
            ((Player) hitterProjectile.getShooter()).sendMessage("The trident did not have a cooldown at this time");
    }
}
