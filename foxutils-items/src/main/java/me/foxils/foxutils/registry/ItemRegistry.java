package me.foxils.foxutils.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utility.ItemUtils;

public final class ItemRegistry {

    private final Map<NamespacedKey, Item> keyToItemTemplate = new HashMap<>();

    public void registerItem(final @NotNull Item item) {
        final NamespacedKey itemKey = item.getKey();

        keyToItemTemplate.put(itemKey, item);

        Bukkit.getLogger().info("Registered: " + itemKey.getKey());
    }

    public void unregisterItem(final @NotNull NamespacedKey itemKey) {
        keyToItemTemplate.remove(itemKey);
        Bukkit.removeRecipe(itemKey);

        Bukkit.getLogger().info("Unregistered: " + itemKey.getKey());
    }

    public void unregisterItem(final @NotNull Item item) {
        unregisterItem(item.getKey());
    }

    public void unregisterPluginItems(final @NotNull Plugin plugin) {
        final String pluginNamespace = plugin.getName().toLowerCase(Locale.ROOT);

        for (final NamespacedKey itemKey : new HashSet<>(keyToItemTemplate.keySet())) {
            if (!itemKey.getNamespace().equals(pluginNamespace))
                continue;

            unregisterItem(itemKey);
        }
    }

    public @Nullable Item getItemFromKey(final @Nullable NamespacedKey itemKey) {
        return keyToItemTemplate.get(itemKey);
    }

    public @Nullable Item getItemFromItemMeta(final @Nullable ItemMeta itemMeta) {
        final NamespacedKey itemKey = ItemUtils.getFoxItemKey(itemMeta);
        if (itemKey == null)
            return null;

        return getItemFromKey(itemKey);
    }

    public @Nullable Item getItemFromItemStack(final @Nullable ItemStack itemStack) {
        if (itemStack == null)
            return null;

        return getItemFromItemMeta(itemStack.getItemMeta());
    }

    public @NotNull List<Item> getRegisteredItems() {
        return new ArrayList<>(keyToItemTemplate.values());
    }
}
