package me.foxils.foxutils.utilities;

import me.foxils.foxutils.Item;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.goatvillage.willow.persistence.PersistentDataContainer;
import net.goatvillage.willow.persistence.PersistentDataType;
import net.goatvillage.willow.NamespacedKey;

import java.util.List;

@SuppressWarnings("unused")
public final class ItemUtils {

    private static final BaseComponent COOLDOWN_PRESENT_MESSAGE =
            new ComponentBuilder("")
                    .append("Wait For Cooldown").bold(true).color(ChatColor.RED)
                    .create()[1];

    private ItemUtils() {}

    public static void nameItem(ItemStack itemStack, String name) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return;

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', org.bukkit.ChatColor.BOLD + name));
        itemStack.setItemMeta(itemMeta);
    }

    public static void addEnchantGlint(ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return;

        itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        itemStack.setItemMeta(itemMeta);
    }

    public static void setCustomModelData(ItemStack itemStack, int customModelData) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return;

        /* TODO: how
        itemMeta.setCustomModelData(customModelData);*/
        itemStack.setItemMeta(itemMeta);
    }

    public static void addItemLore(ItemStack itemStack, List<String> lore) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return;

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    @Nullable
    public static ItemMeta getItemMeta(ItemStack itemStack) {
        if (itemStack == null) return null;

        return itemStack.getItemMeta();
    }

    @Nullable
    private static PersistentDataContainer getPersistentDataContainer(ItemMeta itemMeta) {
        if (itemMeta == null) return null;

        return itemMeta.getPersistentDataContainer();
    }

    public static <Primitive, Complex> boolean storeDataOfType(@NotNull PersistentDataType<Primitive, Complex> type, @NotNull Complex data, @NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        final ItemMeta itemMeta = getItemMeta(itemStack);

        final PersistentDataContainer dataContainer = getPersistentDataContainer(itemMeta);

        if (dataContainer == null) return false;

        dataContainer.set(key, type, data);
        return itemStack.setItemMeta(itemMeta);
    }

    public static boolean storeBooleanData(@NotNull Boolean booleanData, @NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.BOOLEAN, booleanData, key, itemStack);
    }

    public static boolean storeIntegerData(@NotNull Integer integerData, @NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.INTEGER, integerData, key, itemStack);
    }

    public static boolean storeStringData(@NotNull String stringData, @NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.STRING, stringData, key, itemStack);
    }

    @Nullable
    public static <Primitive, Complex> Complex getDataOfType(@NotNull PersistentDataType<Primitive, Complex> type, @NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        final ItemMeta itemMeta = getItemMeta(itemStack);

        final PersistentDataContainer dataContainer = getPersistentDataContainer(itemMeta);

        if (dataContainer == null) return null;

        return dataContainer.get(key, type);
    }

    @NotNull
    public static <Primitive, Complex> Complex getDataOfType(@NotNull PersistentDataType<Primitive, Complex> type, @NotNull NamespacedKey key, @NotNull ItemStack itemStack, @NotNull Complex defaultReturnValue) {
        final Complex getDataResult = getDataOfType(type, key, itemStack);

        return getDataResult == null ? defaultReturnValue : getDataResult;
    }

    @Nullable
    public static Boolean getBooleanData(@NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.BOOLEAN, key, itemStack);
    }

    @Nullable
    public static Integer getIntegerData(@NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.INTEGER, key, itemStack);
    }

    @Nullable
    public static String getStringData(@NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.STRING, key, itemStack);
    }

    public static boolean isFoxItem(@NotNull ItemStack itemStack) {
        final String itemKey = getStringData(Item.itemConfirmationKey, itemStack);

        return itemKey != null;
    }

    public static boolean getCooldown(@NotNull NamespacedKey key, @NotNull ItemStack itemStack, @NotNull Long cooldownInSeconds) {
        final long timeNow = System.currentTimeMillis();
        final long timeLastUsed = getDataOfType(PersistentDataType.LONG, key, itemStack, 0L);

        if ((timeNow - timeLastUsed) < (cooldownInSeconds * 1000)) {
            return true;
        } else {
            storeDataOfType(PersistentDataType.LONG, timeNow, key, itemStack);
            return false;
        }
    }

    public static boolean getCooldown(@NotNull NamespacedKey key, @NotNull ItemStack itemStack, @NotNull Long cooldownInSeconds, @NotNull Player player, @NotNull BaseComponent successMessage, @NotNull BaseComponent unsuccessfulMessage) {
        final boolean cooldownActive = getCooldown(key, itemStack, cooldownInSeconds);

        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1F, 0.75F);

        if (cooldownActive) {
            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1F, 0.5F);
            // TODO: Implementationington in Willow :)
            //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(org.bukkit.ChatColor.DARK_RED + "" + org.bukkit.ChatColor.BOLD + "Wait for cooldown"));
        } else {
            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1F, 1F);
            //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, successMessage);
        }

        return cooldownActive;
    }

    public static boolean getCooldown(@NotNull NamespacedKey key, @NotNull ItemStack itemStack, @NotNull Long cooldownInSeconds, @NotNull Player player, @NotNull BaseComponent successMessage) {
        return getCooldown(key, itemStack, cooldownInSeconds, player, successMessage, COOLDOWN_PRESENT_MESSAGE);
    }

}
