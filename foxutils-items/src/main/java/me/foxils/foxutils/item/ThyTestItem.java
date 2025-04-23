package me.foxils.foxutils.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemaction.AttackAction;
import me.foxils.foxutils.itemaction.ClickActions;
import me.foxils.foxutils.itemaction.CraftItemAction;
import me.foxils.foxutils.itemaction.InventoryClickActions;
import me.foxils.foxutils.itemaction.KillAction;
import me.foxils.foxutils.itemaction.ProjectileHitAction;
import me.foxils.foxutils.itemaction.ProjectileLaunchAction;
import me.foxils.foxutils.itemaction.SelectItemAction;
import me.foxils.foxutils.itemaction.SwapItemHandAction;
import me.foxils.foxutils.utility.ItemAbility;
import me.foxils.foxutils.utility.ItemUtils;

public class ThyTestItem extends Item implements ClickActions, CraftItemAction, InventoryClickActions, KillAction, ProjectileHitAction, ProjectileLaunchAction, SelectItemAction, SwapItemHandAction, AttackAction {

    private final NamespacedKey TRIDENT_TEST_COOLDOWN;

    public ThyTestItem(final @NotNull NamespacedKey key, final @NotNull Plugin plugin, final @NotNull Material itemMaterial, final @NotNull String name, final int customModelData, final @Nullable List<ItemAbility> abilityList, final @Nullable List<ItemStack> itemsForRecipe, final boolean areRecipeItemsExact, final boolean isRecipeShaped) {
        super(key, plugin, itemMaterial, name, customModelData, abilityList, itemsForRecipe, areRecipeItemsExact, isRecipeShaped);

        this.TRIDENT_TEST_COOLDOWN = new NamespacedKey(plugin, "trident_test_cooldown");
    }

    @Override
    public @NotNull ItemStack createItem(final int amount) {
        final ItemStack newItem = super.createItem(amount);
        final ItemMeta itemMeta = newItem.getItemMeta();
        assert itemMeta != null;

        itemMeta.addEnchant(Enchantment.LOYALTY, 3, false);

        newItem.setItemMeta(itemMeta);
        return newItem;
    }

    @Override
    public void onLeftClickAir(@NotNull PlayerInteractEvent event, @NotNull ItemStack itemInteracted) {
        event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Arrow.class, (arrow) -> {
            ItemUtils.addRelatedItemStackUid(arrow, itemInteracted);
            arrow.setShooter(event.getPlayer());
        });
    }

    @Override
    public void onCraftThisItem(final CraftItemEvent craftItemEvent, final ItemStack thisItemStack, final Player crafterPlayer) {
        crafterPlayer.sendMessage("You have crafted a test item somehow tf");
    }

    @Override
    public void onCraftOtherItem(final CraftItemEvent craftItemEvent, final ItemStack craftedItemStack, final ItemStack thisItemStack, final Player crafterPlayer) {
        crafterPlayer.sendMessage("You have crafted another item and the test item detected it");
    }

    @Override
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent, final ItemStack thisItemStack, final @Nullable ItemStack itemStackInCursor) {
        inventoryClickEvent.getWhoClicked().sendMessage("You clicked the test-trident with: " + itemStackInCursor + " in your cursor");
    }

    @Override
    public void onAttackWithThisItem(final @NotNull EntityDamageByEntityEvent entityDamageByEntityEvent, final @NotNull ItemStack thisItemStack, final @NotNull LivingEntity attackedEntity, final @NotNull Player attackingPlayer) {
        attackingPlayer.sendMessage("You attacked with the test item");
    }

    @Override
    public void onAttackWithOtherItem(@NotNull EntityDamageByEntityEvent entityDamageByEntityEvent, @NotNull ItemStack thisItemStack, @Nullable ItemStack itemStackUsedToAttack, @NotNull LivingEntity attackedEntity, @NotNull Player attackingPlayer) {
        attackingPlayer.sendMessage("You attacked with another item and the test item detected it");
    }

    @Override
    public void onInventoryInteract(final InventoryClickEvent inventoryClickEvent, final ItemStack thisItemStack, final @Nullable ItemStack itemStackClickedOn) {
        inventoryClickEvent.getWhoClicked().sendMessage("You clicked: " + itemStackClickedOn + " with the test-trident in your cursor");
    }

    @Override
    public void onKillWithThisItem(final @NotNull EntityDeathEvent playerDeathEvent, final @NotNull ItemStack thisItemStack, final @NotNull LivingEntity killedEntity, final @NotNull Player killerPlayer) {
        killerPlayer.sendMessage("You killed with the test item.");
    }

    @Override
    public void onKillWithOtherItem(final @NotNull EntityDeathEvent playerDeathEvent, final @NotNull ItemStack thisItemStack, final @Nullable ItemStack itemStackUsedToKill, final @NotNull LivingEntity killedEntity, final @NotNull Player killerPlayer) {
        killerPlayer.sendMessage("You killed with another ItemStack and the test item detected it");
    }

    @Override
    public void onProjectileFromThisItemHit(final ProjectileHitEvent projectileHitEvent, final ItemStack thisItemStack, final Projectile hitterProjectile) {
        ((Player) hitterProjectile.getShooter()).sendMessage("you shot with trident and it hit");
    }

    @Override
    public void onProjectileFromOtherItemHit(final ProjectileHitEvent projectileHitEvent, final ItemStack thisItemStack, final ItemStack shootingItemStack, final Projectile hitterProjectile) {
        ((Player) hitterProjectile.getShooter()).sendMessage("A projectile hit from another item in your inventory has been detected by Thy Test Item™");
    }

    @Override
    public void onSelectThisItem(final PlayerItemHeldEvent playerItemHeldEvent, final ItemStack thisItemStack, final @Nullable ItemStack itemStackUnselected) {
        playerItemHeldEvent.getPlayer().sendMessage("You selected the test-trident");
    }

    @Override
    public void onUnselectThisItem(final PlayerItemHeldEvent playerItemHeldEvent, final ItemStack thisItemStack, final @Nullable ItemStack itemStackSelected) {
        playerItemHeldEvent.getPlayer().sendMessage("You unselected the test-trident");
    }

    @Override
    public void onSelectOtherItem(final PlayerItemHeldEvent playerItemHeldEvent, final ItemStack thisItemStack, final @Nullable ItemStack itemStackSelected, final @Nullable ItemStack itemStackUnselected) {
        playerItemHeldEvent.getPlayer().sendMessage("You selected: " + itemStackSelected + " and the test-trident detected it.");
    }

    @Override
    public void onUnselectOtherItem(final PlayerItemHeldEvent playerItemHeldEvent, final ItemStack thisItemStack, final @Nullable ItemStack itemStackUnselected, final @Nullable ItemStack itemStackSelected) {
        playerItemHeldEvent.getPlayer().sendMessage("You unselected: " + itemStackUnselected + " and the test-trident detected it.");
    }

    @Override
    public void onLaunchProjectileWithThisItem(@NotNull ProjectileLaunchEvent projectileLaunchEvent, @NotNull ItemStack thisItemStack, @NotNull Projectile launchedProjectile) {
        ((Player) launchedProjectile.getShooter()).sendMessage("You shot with trident");
    }

    @Override
    public void onLaunchProjectleWithOtherItem(@NotNull ProjectileLaunchEvent projectileLaunchEvent, @NotNull ItemStack thisItemStack, @NotNull ItemStack itemStackUsedToLaunch, Projectile launchedProjectile) {
        ((Player) launchedProjectile.getShooter()).sendMessage("You shot with somethign else and the trident detected it");
    }

    @Override
    public void onSwapOtherItemToMainHand(final PlayerSwapHandItemsEvent playerSwapHandItemsEvent, final ItemStack thisItemStack, final ItemStack itemStackSwappedToMainHand, final ItemStack itemStackSwappedToOffHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage(itemStackSwappedToMainHand + " was swapped to the main hand and the test item detected it");
    }

    @Override
    public void onSwapOtherItemToOffHand(final PlayerSwapHandItemsEvent playerSwapHandItemsEvent, final ItemStack thisItemSTack, final ItemStack itemStackSwappedToOffHand, final ItemStack itemStackSwappedToMainHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage(itemStackSwappedToOffHand + " was swapped to the off hand and the test item detected it");
    }

    @Override
    public void onSwapThisItemToMainHand(final PlayerSwapHandItemsEvent playerSwapHandItemsEvent, final ItemStack thisItemStack, final ItemStack itemStackSwappedToOffHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage("The test item was swapped to the main hand");
    }

    @Override
    public void onSwapThisItemToOffHand(final PlayerSwapHandItemsEvent playerSwapHandItemsEvent, final ItemStack thisItemStack, final ItemStack itemStackSwappedToMainHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage("The test item was swapped to the off hand");
    }

    private void tridentCooldownTest(final ItemStack thisItemStack, final Projectile hitterProjectile) {
        assert hitterProjectile.getShooter() != null;

        if (ItemUtils.getCooldown(TRIDENT_TEST_COOLDOWN, thisItemStack, 10L))
            ((Player) hitterProjectile.getShooter()).sendMessage("The trident has a cooldown bum");
        else
            ((Player) hitterProjectile.getShooter()).sendMessage("The trident did not have a cooldown at this time");
    }
}
