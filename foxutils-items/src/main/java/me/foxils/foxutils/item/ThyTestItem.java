package me.foxils.foxutils.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemaction.AttackAction;
import me.foxils.foxutils.itemaction.ClickActions;
import me.foxils.foxutils.itemaction.CraftItemAction;
import me.foxils.foxutils.itemaction.ProjectileHitAction;
import me.foxils.foxutils.itemaction.ProjectileLaunchAction;
import me.foxils.foxutils.utility.ItemAbility;

public class ThyTestItem extends Item implements AttackAction, ClickActions, CraftItemAction, ProjectileHitAction, ProjectileLaunchAction {

    public ThyTestItem(final @NotNull NamespacedKey key,
                       final @NotNull Plugin plugin,
                       final @NotNull Material itemMaterial,
                       final @NotNull String name,
                       final @Nullable Integer customModelData,
                       final @Nullable List<ItemAbility> abilityList,
                       final @Nullable List<ItemStack> itemsForRecipe,
                       final boolean areRecipeItemsExact,
                       final boolean isRecipeShaped) {
        super(key, plugin, itemMaterial, name, customModelData, abilityList, itemsForRecipe, areRecipeItemsExact, isRecipeShaped);
    }

    // AttackAction
    @Override
    public void onAttackWithThisItem(final @NotNull EntityDamageByEntityEvent entityDamageByEntityEvent,
                                     final @NotNull ItemStack thisItemStack,
                                     final @NotNull LivingEntity attackedEntity,
                                     final @NotNull Player attackingPlayer) {
        attackingPlayer.sendMessage("You have attacked with the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onAttackWithOtherItem(final @NotNull EntityDamageByEntityEvent entityDamageByEntityEvent,
                                      final @NotNull ItemStack thisItemStack,
                                      final @Nullable ItemStack itemStackUsedToAttack,
                                      final @NotNull LivingEntity attackedEntity,
                                      final @NotNull Player attackingPlayer) {
        final String itemStackName;
        if (itemStackUsedToAttack == null)
            itemStackName = "Fist";
        else
            itemStackName = itemStackUsedToAttack.getType().name();

        attackingPlayer.sendMessage("The " + thisItemStack.getItemMeta().getDisplayName() + " has detected an attack from a " + itemStackName);
    }
    // AttackAction End

    // ClickActions
    @Override
    public void onLeftClickAir(final @NotNull PlayerInteractEvent playerInteractEvent,
                               final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You left clicked air using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onLeftClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent,
                                 final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You left clicked a block using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onShiftLeftClickAir(final @NotNull PlayerInteractEvent playerInteractEvent,
                                    final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You shift left clicked air using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onShiftLeftClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent,
                                      final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You shift left clicked a block using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onRightClickAir(final @NotNull PlayerInteractEvent playerInteractEvent,
                                final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You right clicked air using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onRightClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent,
                                  final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You right clicked a block using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onShiftRightClickAir(final @NotNull PlayerInteractEvent playerInteractEvent,
                                     final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You shift right clicked air using the " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onShiftRightClickBlock(final @NotNull PlayerInteractEvent playerInteractEvent,
                                       final @NotNull ItemStack thisItemStack) {
        playerInteractEvent.getPlayer().sendMessage("You shift right clicked a block using the " + thisItemStack.getItemMeta().getDisplayName());
    }
    // ClickActions End

    // CraftItemAction
    @Override
    public void onCraftThisItem(final @NotNull CraftItemEvent craftItemEvent,
                                final @NotNull ItemStack thisItemStack,
                                final @NotNull Player crafterPlayer) {
        crafterPlayer.sendMessage("You've crafted " + getName());
    }

    @Override
    public void onCraftOtherItem(final @NotNull CraftItemEvent craftItemEvent,
                                 final @NotNull ItemStack thisItemStack,
                                 final @NotNull ItemStack craftedItemStack,
                                 final @NotNull Player crafterPlayer) {
        crafterPlayer.sendMessage("You've crafted " + craftedItemStack.getType().name() + " and " + getName() + " has detected it");
    }
    // CraftItemAction End


    @Override
    public void onProjectileFromThisItemHit(final @NotNull ProjectileHitEvent projectileHitEvent,
                                            final @NotNull ItemStack thisItemStack,
                                            final @NotNull Projectile hittingProjectile,
                                            final @NotNull Player shooterPlayer) {
        shooterPlayer.sendMessage("You have hit with a projectile using " + thisItemStack.getItemMeta().getDisplayName());
    }

    @Override
    public void onProjectileFromOtherItemHit(final @NotNull ProjectileHitEvent projectileHitEvent,
                                             final @NotNull ItemStack thisItemStack,
                                             final @NotNull ItemStack itemStackUsedToLaunch,
                                             final @NotNull Projectile hittingProjectile,
                                             final @NotNull Player shooterPlayer) {
        shooterPlayer.sendMessage(
                "You have shot a projectile using " + itemStackUsedToLaunch + " and " + thisItemStack.getItemMeta().getDisplayName() + " has detected it hit");
    }
}
