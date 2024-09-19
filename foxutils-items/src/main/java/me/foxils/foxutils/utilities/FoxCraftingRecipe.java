package me.foxils.foxutils.utilities;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.List;

public class FoxCraftingRecipe {

    // Actual recipe as registered in bukkit
    private final CraftingRecipe convertedRecipe;

    // Self explanatory
    public FoxCraftingRecipe(List<ItemStack> items, NamespacedKey key, ItemStack item, boolean shapedRecipe) {
        if (shapedRecipe) {
            convertedRecipe = new ShapedRecipe(key, item);
            setShapedRecipe(items);
            return;
        }

        convertedRecipe = new ShapelessRecipe(key, item);
        setShapelessRecipe(items);
    }

    // Add all the ingredients in the list to a shapeless recipe
    private void setShapelessRecipe(List<ItemStack> items) {
        ShapelessRecipe recipe = (ShapelessRecipe) convertedRecipe;

        for (ItemStack item : items) {
            recipe.addIngredient(new RecipeChoice.MaterialChoice(item.getType()));
        }
    }

    // Add all the ingredients in the list to a recipe in number order
    private void setShapedRecipe(List<ItemStack> items) {
        ShapedRecipe recipe = (ShapedRecipe) convertedRecipe;

        recipe.shape("123"
                    ,"456"
                    ,"789");

        for (int i = 1; i < 10; i++) {
            ItemStack item = items.get(i-1);

            recipe.setIngredient(Character.forDigit(i, 10), new RecipeChoice.ExactChoice(item));
        }
    }

    // Self explanatory
    public Recipe getConvertedRecipe() {
        return convertedRecipe;
    }

}
