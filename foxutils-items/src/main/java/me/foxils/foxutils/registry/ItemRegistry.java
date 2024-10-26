package me.foxils.foxutils.registry;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.utilities.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

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

    public static Collection<Item> getRegisteredItems() {
        return registeredItems.values();
    }
}