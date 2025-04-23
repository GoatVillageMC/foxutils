package me.foxils.foxutils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.foxils.foxutils.utility.FoxCraftingRecipe;
import me.foxils.foxutils.utility.ItemAbility;
import me.foxils.foxutils.utility.ItemUtils;
import net.md_5.bungee.api.ChatColor;

public abstract class Item {

    private final NamespacedKey key;

    private final Plugin plugin;

    private final Material itemMaterial;
    private final String name;
    private final int customModelData;

    private final List<ItemAbility> abilityList;
    private final List<String> lore;

    private FoxCraftingRecipe recipe;

    public Item(final @NotNull NamespacedKey key, final @NotNull Plugin plugin, final @NotNull Material itemMaterial, final @NotNull String name, final int customModelData, final @Nullable List<ItemAbility> abilityList, final @Nullable List<ItemStack> itemsForRecipe, final boolean areRecipeItemsExact, final boolean isRecipeShaped) {
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
            this.recipe = new FoxCraftingRecipe(itemsForRecipe, key, createItem(1), areRecipeItemsExact, isRecipeShaped);

            final Recipe bukkitRecipe = this.recipe.getConvertedRecipe();

            if (bukkitRecipe == null)
                return;

            Bukkit.addRecipe(bukkitRecipe);
        }, 1L);
    }

    @OverridingMethodsMustInvokeSuper
    public @NotNull ItemStack createItem(final int amount) {
        final ItemStack newItem = new ItemStack(itemMaterial);

        ItemUtils.addCustomName(newItem, name);
        ItemUtils.addItemLore(newItem, getLore());
        ItemUtils.addCustomModelData(newItem, customModelData);

        if (!ItemUtils.addUid(newItem))
            plugin.getLogger().severe(ChatColor.RED + "Unable to add UID to ItemStack '" + newItem + "'");

        // Stores the item class's itemKey (The key that identifies the created ItemStack as an ItemStack of this Item-class) at the foxutils:fox_item location in NBT
        if (!ItemUtils.addItemKey(key, newItem))
            plugin.getLogger().severe(ChatColor.RED + "Unable to add item-key to ItemStack '" + newItem + "'");

        newItem.setAmount(amount);
        return newItem;
    }

    public final void setRecipe(final FoxCraftingRecipe recipe) {
        this.recipe = recipe;
    }

    public @NotNull List<String> getLore() {
        return lore;
    }

    public @Nullable FoxCraftingRecipe getRecipe() {
        return recipe;
    }

    public @NotNull List<ItemAbility> getAbilityList() {
        return abilityList;
    }

    public @NotNull NamespacedKey getKey() {
        return key;
    }

    public @NotNull String getRawName() {
        return getKey().getKey();
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
