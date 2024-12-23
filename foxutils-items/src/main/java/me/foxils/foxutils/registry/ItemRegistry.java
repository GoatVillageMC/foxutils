package me.foxils.foxutils.registry;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public final class ItemRegistry {

    private static final Map<NamespacedKey, Item> REGISTERED_ITEMS = new HashMap<>();

    private ItemRegistry() {
        throw new IllegalStateException("1D brain: instantiate ItemRegistry");
    }

    public static void registerItem(Item item) {
        final NamespacedKey itemKey = item.getKey();

        REGISTERED_ITEMS.put(itemKey, item);

        Bukkit.getLogger().info("Registered: " + itemKey.getKey());
    }

    public static void unregisterItem(NamespacedKey itemKey) {
        REGISTERED_ITEMS.remove(itemKey);

        Bukkit.removeRecipe(itemKey);

        Bukkit.getLogger().info("Unregistered: " + itemKey.getKey());
    }

    public static void unregisterItem(Item item) {
        unregisterItem(item.getKey());
    }

    public static void unregisterPluginItems(Plugin plugin) {
        for (NamespacedKey itemKey : new HashSet<>(REGISTERED_ITEMS.keySet())) {
            if (!itemKey.getNamespace().equals(plugin.getName().toLowerCase(Locale.ROOT)))
                continue;

            unregisterItem(itemKey);
        }
    }

    public static Item getItemFromKey(NamespacedKey key) {
        return REGISTERED_ITEMS.get(key);
    }

    public static Item getItemFromItemStack(@NotNull ItemStack item) {
        final String itemKeyString = ItemUtils.getStringData(Item.ITEM_TYPE_STORAGE, item);

        if (itemKeyString == null)
            return null;

        final NamespacedKey itemKey = NamespacedKey.fromString(itemKeyString);

        if (itemKey == null)
            return null;

        return REGISTERED_ITEMS.get(itemKey);
    }

    public static Collection<Item> getRegisteredItems() {
        return new ArrayList<>(REGISTERED_ITEMS.values());
    }
}