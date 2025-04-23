package me.foxils.foxutils.utility;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@SuppressWarnings("unused")
public class FoxCraftingRecipe {

    // Actual recipe as registered in bukkit
    private final CraftingRecipe convertedRecipe;
    private final NamespacedKey recipeKey;

    public FoxCraftingRecipe(final List<ItemStack> items, final NamespacedKey key, final ItemStack item, final boolean areRecipeItemsExact, final boolean shapedRecipe) {
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

    public Recipe getConvertedRecipe() {
        return convertedRecipe;
    }

    public NamespacedKey getRecipeKey() {
        return recipeKey;
    }

    // Add all the ingredients in the list to a shapeless recipe
    private void setShapelessRecipe(final List<ItemStack> items, final boolean areRecipeItemsExact) {
        final ShapelessRecipe recipe = (ShapelessRecipe) convertedRecipe;

        for (final ItemStack item : items) {
            if (item == null || item.getType() == Material.AIR)
                continue;

            if (!areRecipeItemsExact)
                recipe.addIngredient(new RecipeChoice.MaterialChoice(item.getType()));
            else
                recipe.addIngredient(new RecipeChoice.ExactChoice(item));
        }
    }

    // Add all the ingredients in the list to a recipe in number order
    private void setShapedRecipe(final List<ItemStack> items, final boolean areRecipeItemsExact) {
        final ShapedRecipe recipe = (ShapedRecipe) convertedRecipe;

        recipe.shape("123", "456", "789");

        for (int i = 1; i < 10; i++) {
            final ItemStack item = items.get(i - 1);

            if (item == null || item.getType() == Material.AIR)
                continue;

            if (!areRecipeItemsExact)
                recipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.MaterialChoice(item.getType()));
            else
                recipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.ExactChoice(item));
        }
    }
}
