package me.foxils.foxutils;

import me.foxils.foxutils.utilities.FoxCraftingRecipe;
import me.foxils.foxutils.utilities.ItemAbility;
import me.foxils.foxutils.utilities.ItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class Item {

    private final NamespacedKey ITEM_KEY;

    private final Plugin plugin;

    private final Material itemMaterial;
    private final String name;
    private final int customModelData;

    private final List<ItemAbility> abilityList;
    private final List<String> lore;

    private FoxCraftingRecipe recipe;

    public Item(@NotNull Plugin plugin, @NotNull Material itemMaterial, @NotNull String name, int customModelData, @Nullable List<ItemAbility> abilityList, @Nullable List<ItemStack> itemsForRecipe, boolean isRecipeShaped) {
        this.ITEM_KEY = new NamespacedKey(plugin, ChatColor.stripColor(name).replace("[", "").replace("]", "").replace(" ", "_").replace("'", "").toLowerCase());

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

            for (ItemAbility itemAbility : abilityList) {
                lore.addAll(itemAbility.toLore());

                if (!lastAbility.equals(itemAbility))
                    lore.add(" ");
            }
        }

        // TODO: Needs to be done differently asap.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.recipe = new FoxCraftingRecipe(itemsForRecipe, ITEM_KEY, createItem(1), isRecipeShaped);

            final Recipe bukkitRecipe = this.recipe.getConvertedRecipe();

            if (bukkitRecipe == null)
                return;

            Bukkit.addRecipe(bukkitRecipe);
        }, 1L);
    }

    @OverridingMethodsMustInvokeSuper
    public @NotNull ItemStack createItem(int amount) {
        final ItemStack newItem = new ItemStack(itemMaterial);

        ItemUtils.addCustomName(newItem, name);
        ItemUtils.addItemLore(newItem, getLore());
        ItemUtils.addCustomModelData(newItem, customModelData);

        if (!ItemUtils.addUid(newItem))
            plugin.getLogger().severe(ChatColor.RED + "Unable to add UID to ItemStack (" + newItem + ") of Item-Class: " + getClass());

        // Stores the item class's itemKey (The key that identifies the created ItemStack as an ItemStack of this Item-class) at the foxutils:fox_item location in NBT
        if (!ItemUtils.addItemKey(ITEM_KEY, newItem))
            plugin.getLogger().severe(ChatColor.RED + "Unable to add itemKey to ItemStack (" + newItem + ")");

        newItem.setAmount(amount);
        return newItem;
    }

    public final void setRecipe(FoxCraftingRecipe recipe) {
        this.recipe = recipe;
    }

    public @NotNull List<String> getLore() {
        return lore;
    }

    public final @Nullable FoxCraftingRecipe getRecipe() {
        return recipe;
    }

    public final @NotNull List<ItemAbility> getAbilityList() {
        return abilityList;
    }

    public final @NotNull NamespacedKey getKey() {
        return ITEM_KEY;
    }

    public final @NotNull String getRawName() {
        return getKey().getKey();
    }

    public final @NotNull String getName() {
        return name;
    }

    public final @NotNull Material getItemMaterial() {
        return itemMaterial;
    }

    public final @NotNull Plugin getPlugin() {
        return plugin;
    }
}
