package me.foxils.foxutils;

import me.foxils.foxutils.utilities.FoxCraftingRecipe;
import me.foxils.foxutils.utilities.ItemAbility;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {

    private final ItemStack item;
    private final String name;
    private final int customModelData;

    private final List<ItemAbility> abilityList;
    private FoxCraftingRecipe recipe;
    
    private static final NamespacedKey itemConfirmationKey = new NamespacedKey(Items.getInstance(), "itemkey");
    private final NamespacedKey itemKey;

    // Material (what the custom item looks like), Name (what the item is called/actually named as), itemKey (refrence to the item internally, for example in the ItemRegistry class), ItemsForRecipe (self-explanatory), shapedRecipe (self-explanatory)
    public Item(Material material, int customModelData, String name, NamespacedKey itemKey, List<ItemAbility> abilityList, List<ItemStack> itemsForRecipe, boolean shapedRecipe) {
        this.item = new ItemStack(material);
        this.name = name;
        this.customModelData = customModelData;

        this.itemKey = itemKey;

        this.abilityList = abilityList;
        recipe = new FoxCraftingRecipe(itemsForRecipe, itemKey, createItem(1), shapedRecipe);
    }

    public Item(Material material, String name, NamespacedKey itemKey, List<ItemAbility> abilityList, List<ItemStack> itemsForRecipe, boolean shapedRecipe) {
        this.item = new ItemStack(material);
        this.name = name;
        this.customModelData = 0;

        this.itemKey = itemKey;

        this.abilityList = abilityList;
        recipe = new FoxCraftingRecipe(itemsForRecipe, itemKey, createItem(1), shapedRecipe);
    }

    public Item(Material material, int customModelData, String name, NamespacedKey itemKey, List<ItemAbility> abilityList) {
        this.item = new ItemStack(material);
        this.name = name;
        this.customModelData = customModelData;

        this.itemKey = itemKey;

        this.abilityList = abilityList;
        this.recipe = null;
    }

    public Item(Material material, String name, NamespacedKey itemKey, List<ItemAbility> abilityList) {
        this.item = new ItemStack(material);
        this.name = name;
        this.customModelData = 0;

        this.itemKey = itemKey;

        this.abilityList = abilityList;
        this.recipe = null;
    }

    public ItemStack createItem(int amount) {
        ItemStack newItem = item.clone();

        ItemUtils.nameItem(newItem, name);
        ItemUtils.addItemLore(item, createLore());
        ItemUtils.setCustomModelData(newItem, customModelData);
        // Stores the actual item itemKey (name/internal refrence), at the items:itemkey location in NBT
        ItemUtils.storeStringData(itemConfirmationKey, newItem, itemKey.toString());

        newItem.setAmount(amount);

        return newItem;
    }

    public List<String> createLore() {
        List<String> lore = new ArrayList<>();

        lore.add(" ");

        abilityList.forEach(itemAbility -> {
            lore.addAll(itemAbility.toLore());
            if (!abilityList.getLast().equals(itemAbility)) {
                lore.add(" ");
            }
        });

        return lore;
    }

    public Material getItemMaterial() {
        return item.getType();
    }

    public ItemStack getRawItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public NamespacedKey getKey() {
        return itemKey;
    }

    public FoxCraftingRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(FoxCraftingRecipe recipe) {
        this.recipe = recipe;
    }
}
