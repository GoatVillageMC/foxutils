package me.foxils.foxutils.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utility.ItemUtils;

@SuppressWarnings("unused")
public final class ItemRegistry {

    private static final Map<NamespacedKey, Item> REGISTERED_ITEMS = new HashMap<>();

    public static void registerItem(final @NotNull Item item) {
        final NamespacedKey itemKey = item.getKey();

        REGISTERED_ITEMS.put(itemKey, item);

        Bukkit.getLogger().info("Registered: " + itemKey.getKey());
    }

    public static void unregisterItem(final @NotNull NamespacedKey itemKey) {
        REGISTERED_ITEMS.remove(itemKey);
        Bukkit.removeRecipe(itemKey);

        Bukkit.getLogger().info("Unregistered: " + itemKey.getKey());
    }

    public static void unregisterItem(final @NotNull Item item) {
        unregisterItem(item.getKey());
    }

    public static void unregisterPluginItems(final @NotNull Plugin plugin) {
        final String pluginNamespace = plugin.getName().toLowerCase(Locale.ROOT);

        for (final NamespacedKey itemKey : new HashSet<>(REGISTERED_ITEMS.keySet())) {
            if (!itemKey.getNamespace().equals(pluginNamespace))
                continue;

            unregisterItem(itemKey);
        }
    }

    public static @Nullable Item getItemFromKey(final @Nullable NamespacedKey key) {
        return REGISTERED_ITEMS.get(key);
    }

    public static @Nullable Item getItemFromItemStack(final @Nullable ItemStack itemStack) {
        if (itemStack == null)
            return null;

        final NamespacedKey itemKey = ItemUtils.getFoxItemKey(itemStack);

        if (itemKey == null)
            return null;

        return REGISTERED_ITEMS.get(itemKey);
    }

    public static @NotNull Collection<Item> getRegisteredItems() {
        return new ArrayList<>(REGISTERED_ITEMS.values());
    }

    private ItemRegistry() {
        throw new IllegalStateException("1D brain: instantiate ItemRegistry");
    }
}
