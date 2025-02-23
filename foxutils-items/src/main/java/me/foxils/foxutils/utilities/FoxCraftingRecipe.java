package me.foxils.foxutils.utilities;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;

@SuppressWarnings("unused")
public class FoxCraftingRecipe {

    // Actual recipe as registered in bukkit
    private final CraftingRecipe convertedRecipe;
    private final NamespacedKey recipeKey;

    public FoxCraftingRecipe(List<ItemStack> items, NamespacedKey key, ItemStack item, boolean areRecipeItemsExact, boolean shapedRecipe) {
        this.recipeKey = key;

        if (items == null || items.isEmpty()) {
            this.convertedRecipe = null;
            return;
        }

        if (shapedRecipe) {
            this.convertedRecipe = new ShapedRecipe(key, item);
            setShapedRecipe(items, areRecipeItemsExact);
            return;
        }

        this.convertedRecipe = new ShapelessRecipe(key, item);
        setShapelessRecipe(items, areRecipeItemsExact);
    }

    // Add all the ingredients in the list to a shapeless recipe
    private void setShapelessRecipe(List<ItemStack> items, boolean areRecipeItemsExact) {
        ShapelessRecipe recipe = (ShapelessRecipe) convertedRecipe;

        for (ItemStack item : items) {
            if (item == null || item.getType() == Material.AIR)
                continue;

            if (!areRecipeItemsExact)
                recipe.addIngredient(new RecipeChoice.MaterialChoice(item.getType()));
            else
                recipe.addIngredient(new RecipeChoice.ExactChoice(item));
        }
    }

    // Add all the ingredients in the list to a recipe in number order
    private void setShapedRecipe(List<ItemStack> items, boolean areRecipeItemsExact) {
        ShapedRecipe recipe = (ShapedRecipe) convertedRecipe;

        recipe.shape("123"
                    ,"456"
                    ,"789");

        for (int i = 1; i < 10; i++) {
            ItemStack item = items.get(i-1);

            if (item == null || item.getType() == Material.AIR)
                continue;

            if (!areRecipeItemsExact)
                recipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.MaterialChoice(item.getType()));
            else
                recipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.ExactChoice(item));
        }
    }

    public Recipe getConvertedRecipe() {
        return convertedRecipe;
    }

    public NamespacedKey getRecipeKey() {
        return recipeKey;
    }
}
