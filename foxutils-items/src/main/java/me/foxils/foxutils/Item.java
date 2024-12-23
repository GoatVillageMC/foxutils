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
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class Item {

    protected final Plugin plugin;

    private final ItemStack item;
    private final String name;
    private final int customModelData;

    private final List<String> actualLore = new ArrayList<>();

    private final List<ItemAbility> abilityList = new ArrayList<>();
    private FoxCraftingRecipe recipe;

    private final NamespacedKey itemKey;

    @SuppressWarnings("all")
    public static final NamespacedKey ITEM_TYPE_STORAGE = new NamespacedKey("foxutils", "fox_item");
    public static final NamespacedKey ITEM_UID_STORAGE = new NamespacedKey("foxutils", "item_uid_storage");

    public Item(@NotNull Material material, int customModelData, @NotNull String name, @NotNull Plugin plugin, @Nullable List<ItemAbility> abilityList, @Nullable List<ItemStack> itemsForRecipe, boolean shapedRecipe) {
        this.plugin = plugin;

        this.item = new ItemStack(material);
        this.name = name;
        this.customModelData = customModelData;

        this.itemKey = new NamespacedKey(plugin, ChatColor.stripColor(name).replace("[", "").replace("]", "").replace(" ", "_").replace("'", "").toLowerCase());

        if (abilityList != null)
            this.abilityList.addAll(abilityList);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            this.recipe = new FoxCraftingRecipe(itemsForRecipe, itemKey, createItem(1), shapedRecipe);

            //TODO: Move to the FoxCraftingRecipe Class somehow
            final Recipe bukkitRecipe = this.recipe.getConvertedRecipe();

            if (bukkitRecipe == null)
                return;

            Bukkit.addRecipe(bukkitRecipe);
        }, 1L);
    }

    public Item(Material material, String name, Plugin plugin, List<ItemAbility> abilityList, List<ItemStack> itemsForRecipe, boolean shapedRecipe) {
        this(material, 0, name, plugin, abilityList, itemsForRecipe, shapedRecipe);
    }

    public Item(Material material, int customModelData, String name, Plugin plugin, List<ItemAbility> abilityList) {
        this(material, customModelData, name, plugin, abilityList, null, false);
    }

    public Item(Material material, String name, Plugin plugin, List<ItemAbility> abilityList) {
        this(material, 0, name, plugin, abilityList);
    }

    @OverridingMethodsMustInvokeSuper
    public ItemStack createItem(int amount) {
        final ItemStack newItem = item.clone();

        if (!ItemUtils.addUid(newItem)) {
            final Logger pluginLogger = plugin.getLogger();

            pluginLogger.severe(ChatColor.RED + "CRITICAL ITEM CREATION ERROR");
            pluginLogger.severe(ChatColor.RED + "CRITICAL ITEM CREATION ERROR");
            pluginLogger.severe(ChatColor.RED + "CRITICAL ITEM CREATION ERROR");
            pluginLogger.severe(ChatColor.RED + "CRITICAL ITEM CREATION ERROR");

            pluginLogger.severe(ChatColor.RED + "Unable to add UID to ItemStack of " + this.getClass() + " Item-Class");
        }
        ItemUtils.addCustomName(newItem, name);
        ItemUtils.addItemLore(newItem, createLore());
        ItemUtils.addCustomModelData(newItem, customModelData);

        // Stores the item class's itemKey (identifier key), at the foxutils:fox_item location in NBT
        if (!ItemUtils.storeStringData(itemKey.toString(), ITEM_TYPE_STORAGE, newItem))
            plugin.getLogger().severe("Could not add itemKey to item");

        newItem.setAmount(amount);

        return newItem;
    }

    public List<String> createLore() {
        if (!this.actualLore.isEmpty())
            return this.actualLore;

        final List<String> lore = new ArrayList<>();

        lore.add(" ");

        for (ItemAbility itemAbility : abilityList) {
            lore.addAll(itemAbility.toLore());

            if (!this.abilityList.getLast().equals(itemAbility))
                lore.add(" ");
        }

        this.actualLore.addAll(lore);

        return lore;
    }

    public final Material getItemMaterial() {
        return item.getType();
    }

    public final ItemStack getRawItem() {
        return item;
    }

    public final String getName() {
        return name;
    }

    public final NamespacedKey getKey() {
        return itemKey;
    }

    public final String getRawName() {
        return getKey().getKey();
    }

    public final FoxCraftingRecipe getRecipe() {
        return recipe;
    }

    public final void setRecipe(FoxCraftingRecipe recipe) {
        this.recipe = recipe;
    }
}
