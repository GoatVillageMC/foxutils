package me.foxils.foxutils.registry;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utilities.FoxCraftingRecipe;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.*;

@SuppressWarnings("unused")
public final class ItemRegistry {

    //prettey simple you can probably get the jist of it

    private static final HashMap<NamespacedKey, Item> registeredItems = new HashMap<>();

    private ItemRegistry() {
        throw new IllegalStateException("1D brain: instantiate ItemRegistry");
    }

    public static void registerItem(Item item) {
        final NamespacedKey itemKey = item.getKey();

        registeredItems.put(itemKey, item);

        Bukkit.getLogger().info("Registered: " + itemKey.getKey());

        // should probably move to the FoxCraftingRecipe Class
        // made more sense here earlier
        final FoxCraftingRecipe itemRecipe = item.getRecipe();

        if (itemRecipe == null) {
            return;
        }

        final Recipe bukkitRecipe = itemRecipe.getConvertedRecipe();

        if (bukkitRecipe == null) return;

        Bukkit.addRecipe(bukkitRecipe);
    }

    public static Item getItemFromKey(NamespacedKey key) {
        return registeredItems.get(key);
    }

    public static Item getItemFromItemStack(ItemStack item) {
        if (item == null) {
            return null;
        }

        if (item.getType() == Material.AIR) {
            return null;
        }

        if (!item.hasItemMeta()) {
            return null;
        }

        String itemKeyString = ItemUtils.getStringDataFromWeaponKey(Item.itemConfirmationKey, item);

        if (itemKeyString == null) {
            return null;
        }

        NamespacedKey itemKey = NamespacedKey.fromString(itemKeyString);

        if (itemKey == null) {
            return null;
        }

        return registeredItems.get(itemKey);
    }

    public static HashSet<Item> getRegisteredGems() {
        return new HashSet<>(registeredItems.values());
    }
}