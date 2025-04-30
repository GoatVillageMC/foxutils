package me.foxils.foxutils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.foxils.foxutils.FoxutilsItems.PDCLocationKey;
import me.foxils.foxutils.utility.FoxCraftingRecipe;
import me.foxils.foxutils.utility.ItemAbility;
import me.foxils.foxutils.utility.ItemUtils;

public abstract class Item {

    private final NamespacedKey key;

    private final Plugin plugin;
    private final Material itemMaterial;

    private final String name;

    private final Integer customModelData;

    private final List<ItemAbility> abilityList;

    private final List<String> lore;

    private FoxCraftingRecipe recipe;

    public Item(final @NotNull NamespacedKey key,
                final @NotNull Plugin plugin,
                final @NotNull Material itemMaterial,
                final @NotNull String name,
                final @Nullable Integer customModelData,
                final @Nullable List<ItemAbility> abilityList,
                final @Nullable List<ItemStack> itemsForRecipe, // This and below params are for recipe object
                final boolean areRecipeItemsExact,
                final boolean isRecipeShaped) {
        this.key = key;

        this.plugin = plugin;

        this.itemMaterial = itemMaterial;
        this.name = name;
        this.customModelData = customModelData;

        this.abilityList = new ArrayList<>();
        this.lore = new ArrayList<>();

        if (abilityList != null && !abilityList.isEmpty()) {
            this.abilityList.addAll(abilityList);

            final ItemAbility lastAbility = abilityList.getLast();

            lore.add(" ");

            for (final ItemAbility itemAbility : abilityList) {
                lore.addAll(itemAbility.toLore());

                if (!lastAbility.equals(itemAbility))
                    lore.add(" ");
            }
        }

        // TODO: Needs to be done differently asap.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.recipe = new FoxCraftingRecipe(itemsForRecipe, key, createItem(1), areRecipeItemsExact,
                    isRecipeShaped);

            final Recipe bukkitRecipe = this.recipe.getConvertedRecipe();

            if (bukkitRecipe == null)
                return;

            Bukkit.addRecipe(bukkitRecipe);
        }, 1L);
    }

    @OverridingMethodsMustInvokeSuper
    public @NotNull ItemStack createItem(final int amount) {
        final ItemStack newItem = new ItemStack(itemMaterial, amount);
        final ItemMeta itemMeta = newItem.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(customModelData);
        itemMeta.setLore(getLore());

        // Stores the item class's itemKey (The key that identifies the created
        // ItemStack as an ItemStack of whatever Item-class (template)) at the foxutils-items:fox_item location
        ItemUtils.storeStringData(key.toString(), PDCLocationKey.ITEMSTACK_ITEMKEY_STORAGE, itemMeta);
        ItemUtils.storeStringData(UUID.randomUUID().toString(), PDCLocationKey.ITEMSTACK_UID_STORAGE, itemMeta);

        newItem.setItemMeta(itemMeta);
        return newItem;
    }

    public final void setRecipe(final FoxCraftingRecipe recipe) {
        this.recipe = recipe;
    }

    public @Nullable List<String> getLore() {
        return new ArrayList<>(lore);
    }

    public @Nullable FoxCraftingRecipe getRecipe() {
        return recipe;
    }

    public @NotNull List<ItemAbility> getAbilityList() {
        return new ArrayList<>(abilityList);
    }

    public @NotNull NamespacedKey getKey() {
        return key;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Material getItemMaterial() {
        return itemMaterial;
    }

    protected @NotNull Plugin getPlugin() {
        return plugin;
    }
}
