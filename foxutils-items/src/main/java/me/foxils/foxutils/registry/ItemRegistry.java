package me.foxils.foxutils.registry;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Bukkit;
import net.goatvillage.willow.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class ItemRegistry {

    private static final Map<NamespacedKey, Item> registeredItems = new HashMap<>();

    private ItemRegistry() {
        throw new IllegalStateException("1D brain: instantiate ItemRegistry");
    }

    public static void registerItem(Item item) {
        final NamespacedKey itemKey = item.getKey();

        registeredItems.put(itemKey, item);

        Bukkit.getLogger().info("Registered: " + itemKey.getKey());
    }

    public static Item getItemFromKey(NamespacedKey key) {
        return registeredItems.get(key);
    }

    public static Item getItemFromItemStack(@NotNull ItemStack item) {
        final String itemKeyString = ItemUtils.getStringData(Item.itemConfirmationKey, item);

        if (itemKeyString == null) {
            return null;
        }

        final NamespacedKey itemKey = NamespacedKey.fromString(itemKeyString);

        if (itemKey == null) {
            return null;
        }

        return registeredItems.get(itemKey);
    }

    public static Collection<Item> getRegisteredItems() {
        return new ArrayList<>(registeredItems.values());
    }
}