package me.foxils.foxutils;

import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

@SuppressWarnings("unused")
public final class ItemRegistry {

    //prettey simple you can probably get the jist of it

    private static final Plugin plugin = Items.getInstance();
    private static final HashMap<NamespacedKey, Item> registeredItems = new HashMap<>();

    private ItemRegistry() {
        throw new IllegalStateException("1D brain: instantiate ItemRegistry");
    }

    public static void registerItem(Item item) {
        plugin.getLogger().info("registered" + item.getName());

        registeredItems.put(item.getKey(), item);

        if (item instanceof Listener listenerItem) {
            plugin.getServer().getPluginManager().registerEvents(listenerItem, plugin);
        }

        // should probably move to the FoxCraftingRecipe Class
        // made more sense here earlier
        if (item.getRecipe() == null) {
            return;
        }
        Bukkit.addRecipe(item.getRecipe().getConvertedRecipe());
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

        if (!registeredItems.containsKey(itemKey)) {
            return null;
        }

        return registeredItems.get(itemKey);
    }
}