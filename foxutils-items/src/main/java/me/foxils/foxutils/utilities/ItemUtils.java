package me.foxils.foxutils.utilities;

import me.foxils.foxutils.Item;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class ItemUtils {

    private ItemUtils() {}

    public static ItemStack nameItem(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        String translated = ChatColor.translateAlternateColorCodes('&', org.bukkit.ChatColor.BOLD + name);

        itemMeta.setItemName(translated);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static void addEnchantGlint(ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    public static ItemStack setCustomModelData(ItemStack item, int customModelData) {
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setCustomModelData(customModelData);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack addItemLore(ItemStack item, List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    private static List<Object> getPersistentDataContainer(ItemStack item) {
        if (item == null) {
            return null;
        }

        if (!item.hasItemMeta()) {
            return null;
        }

        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        return Arrays.asList(
                itemMeta,
                itemMeta.getPersistentDataContainer()
        );
    }

    public static <Primitive, Complex> ItemStack storeDataOfType(PersistentDataType<Primitive, Complex> type, Complex data, NamespacedKey key, ItemStack item) {
        List<Object> containerData = getPersistentDataContainer(item);

        PersistentDataContainer dataContainer = (PersistentDataContainer) containerData.getLast();

        dataContainer.set(key, type, data);

        item.setItemMeta((ItemMeta) containerData.getFirst());

        return item;
    }

    public static ItemStack storeIntegerData(NamespacedKey key, ItemStack item, Integer integer) {
        return storeDataOfType(PersistentDataType.INTEGER, integer, key, item);
    }

    public static ItemStack storeStringData(NamespacedKey key, ItemStack item, String string) {
        return storeDataOfType(PersistentDataType.STRING, string, key, item);
    }

    public static <Primitive, Complex> Complex getDataOfType(PersistentDataType<Primitive, Complex> type, NamespacedKey key, ItemStack item) {
        PersistentDataContainer dataContainer = (PersistentDataContainer) getPersistentDataContainer(item).getLast();

        if (!(dataContainer.has(key))) {
            return null;
        }

        return dataContainer.get(key, type);
    }

    public static boolean isFoxItem(ItemStack itemStack) {
        String getConfirmationResult = getStringDataFromWeaponKey(Item.itemConfirmationKey, itemStack);

        return getConfirmationResult != null;
    }

    public static String getStringDataFromWeaponKey(NamespacedKey key, ItemStack item) {
        return getDataOfType(PersistentDataType.STRING, key, item);
    }

    public static Integer getIntegerDataFromWeaponKey(NamespacedKey key, ItemStack item) {
        return getDataOfType(PersistentDataType.INTEGER, key, item);
    }

    public static boolean getCooldown(NamespacedKey key, ItemStack item, int cooldown, Player player, TextComponent successMessage) {
        Double timeNow = (double) (System.currentTimeMillis() / 1000);
        Double timeLastUsed = getDataOfType(PersistentDataType.DOUBLE, key, item);

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.75F);
        // Should also implement the "Wait for cooldown, etc" but will wait until bar api is written because
        // then I don't have to rewrite stuff.
        // Actually nvm

        if (timeLastUsed == null) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, successMessage);
            storeDataOfType(PersistentDataType.DOUBLE, timeNow, key, item);
            return false;
        } else if ((timeNow - timeLastUsed) >= cooldown) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, successMessage);
            storeDataOfType(PersistentDataType.DOUBLE, timeNow, key, item);
            return false;
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.5F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(org.bukkit.ChatColor.DARK_RED + "" + org.bukkit.ChatColor.BOLD + "Wait for cooldown"));
            return true;
        }
    }

    public static boolean getCooldown(NamespacedKey key, ItemStack item, int cooldown) {
        Double timeNow = (double) (System.currentTimeMillis() / 1000);
        Double timeLastUsed = getDataOfType(PersistentDataType.DOUBLE, key, item);

        if (timeLastUsed == null) {
            storeDataOfType(PersistentDataType.DOUBLE, timeNow, key, item);
            return false;
        } else if ((timeNow - timeLastUsed) >= cooldown) {
            storeDataOfType(PersistentDataType.DOUBLE, timeNow, key, item);
            return false;
        } else {
            return true;
        }
    }

}
