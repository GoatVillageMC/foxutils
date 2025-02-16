package me.foxils.foxutils.items;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemactions.*;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThyTestItem extends Item implements ClickActions, CraftItemAction, InventoryClickActions, KillActions, ProjectileHitAction, ProjectileLaunchAction, SelectItemActions, SwapItemHandActions {

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
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackInCursor) {
        inventoryClickEvent.getWhoClicked().sendMessage("You clicked the test-trident with: " + itemStackInCursor + " in your cursor");
    }

    @Override
    public void onInventoryInteract(InventoryClickEvent inventoryClickEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackClickedOn) {
        inventoryClickEvent.getWhoClicked().sendMessage("You clicked: " + itemStackClickedOn + " with the test-trident in your cursor");
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
    public void onProjectileFromThisItemHit(ProjectileHitEvent projectileHitEvent, ItemStack thisItemStack, Projectile hitterProjectile) {
        tridentCooldownTest(thisItemStack, hitterProjectile);
    }

    private void tridentCooldownTest(ItemStack thisItemStack, Projectile hitterProjectile) {
        assert hitterProjectile.getShooter() != null;

        if (ItemUtils.getCooldown(TRIDENT_TEST_COOLDOWN, thisItemStack, 10L))
            ((Player) hitterProjectile.getShooter()).sendMessage("The trident has a cooldown bum");
        else
            ((Player) hitterProjectile.getShooter()).sendMessage("The trident did not have a cooldown at this time");
    }

    @Override
    public void onProjectileFromOtherItemHit(ProjectileHitEvent projectileHitEvent, ItemStack thisItemStack, ItemStack shootingItemStack, Projectile hitterProjectile) {
        assert hitterProjectile.getShooter() != null;

        ((Player) hitterProjectile.getShooter()).sendMessage("A projectile shot from another item in your inventory has been detected by Thy Test Item™");
    }

    @Override
    public void onSelectThisItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackUnselected) {
        playerItemHeldEvent.getPlayer().sendMessage("You selected the test-trident");
    }

    @Override
    public void onUnselectThisItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackSelected) {
        playerItemHeldEvent.getPlayer().sendMessage("You unselected the test-trident");
    }

    @Override
    public void onSelectOtherItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackSelected, @Nullable ItemStack itemStackUnselected) {
        playerItemHeldEvent.getPlayer().sendMessage("You selected: " + itemStackSelected + " and the test-trident detected it.");
    }

    @Override
    public void onUnselectOtherItem(PlayerItemHeldEvent playerItemHeldEvent, ItemStack thisItemStack, @Nullable ItemStack itemStackUnselected, @Nullable ItemStack itemStackSelected) {
        playerItemHeldEvent.getPlayer().sendMessage("You unselected: " + itemStackUnselected + " and the test-trident detected it.");
    }

    @Override
    public void onSwapOtherItemToMainHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemStack, ItemStack itemStackSwappedToMainHand, ItemStack itemStackSwappedToOffHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage(itemStackSwappedToMainHand + " was swapped to the main hand and the test item detected it");
    }

    @Override
    public void onSwapOtherItemToOffHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemSTack, ItemStack itemStackSwappedToOffHand, ItemStack itemStackSwappedToMainHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage(itemStackSwappedToOffHand + " was swapped to the off hand and the test item detected it");
    }

    @Override
    public void onSwapThisItemToMainHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemStack, ItemStack itemStackSwappedToOffHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage("The test item was swapped to the main hand");
    }

    @Override
    public void onSwapThisItemToOffHand(PlayerSwapHandItemsEvent playerSwapHandItemsEvent, ItemStack thisItemStack, ItemStack itemStackSwappedToMainHand) {
        playerSwapHandItemsEvent.getPlayer().sendMessage("The test item was swapped to the off hand");
    }
}
