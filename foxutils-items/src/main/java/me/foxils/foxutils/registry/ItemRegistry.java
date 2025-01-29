package me.foxils.foxutils.registry;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Bukkit;
import net.goatvillage.willow.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unused")
public final class ItemRegistry {

    private static final Map<NamespacedKey, Item> REGISTERED_ITEMS = new HashMap<>();

    private ItemRegistry() {
        throw new IllegalStateException("1D brain: instantiate ItemRegistry");
    }

    public static void registerItem(@NotNull Item item) {
        final NamespacedKey itemKey = item.getKey();

        REGISTERED_ITEMS.put(itemKey, item);

        Bukkit.getLogger().info("Registered: " + itemKey.getKey());
    }

    public static void unregisterItem(@NotNull NamespacedKey itemKey) {
        REGISTERED_ITEMS.remove(itemKey);
        /* TODO: Create method inside Server and CraftServer "removeRecipe()"
           NOTE: Requires additional modification to Recipe-classes to include an identifier (Use NamespacedKey)
        Bukkit.removeRecipe(itemKey); */

        Bukkit.getLogger().info("Unregistered: " + itemKey.getKey());
    }

    public static void unregisterItem(@NotNull Item item) {
        unregisterItem(item.getKey());
    }

    public static void unregisterPluginItems(@NotNull Plugin plugin) {
        final String pluginNamespace = plugin.getName().toLowerCase(Locale.ROOT);

        for (NamespacedKey itemKey : new HashSet<>(REGISTERED_ITEMS.keySet())) {
            if (!itemKey.getNamespace().equals(pluginNamespace))
                continue;

            unregisterItem(itemKey);
        }
    }

    public static @Nullable Item getItemFromKey(@Nullable NamespacedKey key) {
        return REGISTERED_ITEMS.get(key);
    }

    public static @Nullable Item getItemFromItemStack(@Nullable ItemStack itemStack) {
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
}