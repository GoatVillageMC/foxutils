package me.foxils.foxutils.utility;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

@SuppressWarnings("UnstableApiUsage")
public final class ItemUtils {

    private static final BaseComponent COOLDOWN_PRESENT_MESSAGE = new ComponentBuilder().append("Wait For Cooldown").bold(true).color(ChatColor.RED).build();

    private static final NamespacedKey ITEMSTACK_ITEMKEY_STORAGE = new NamespacedKey("foxutils", "fox_item");
    private static final NamespacedKey ITEMSTACK_UID_STORAGE = new NamespacedKey("foxutils", "item_uid_storage");

    private static final NamespacedKey DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE = new NamespacedKey("foxutils", "related_itemstack_uids_storage");

    public static void addCustomName(final ItemStack itemStack, final String name) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null)
            return;

        itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&', org.bukkit.ChatColor.BOLD + name));
        itemStack.setItemMeta(itemMeta);
    }

    public static void addEnchantGlint(final ItemStack itemStack) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null)
            return;

        itemMeta.setEnchantmentGlintOverride(true);
        itemStack.setItemMeta(itemMeta);
    }

    public static void addCustomModelData(final ItemStack itemStack, final int customModelData) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null)
            return;

        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
    }

    public static void addItemLore(final ItemStack itemStack, final List<String> lore) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null)
            return;

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public static <Primitive, Complex> boolean storeDataOfType(final @NotNull PersistentDataType<Primitive, Complex> type, final @NotNull Complex data, final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        if (itemStack.getType() == Material.AIR)
            return false;

        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        final PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

        dataContainer.set(key, type, data);
        return itemStack.setItemMeta(itemMeta);
    }

    public static boolean storeBooleanData(final @NotNull Boolean booleanData, final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.BOOLEAN, booleanData, key, itemStack);
    }

    public static boolean storeIntegerData(final @NotNull Integer integerData, final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.INTEGER, integerData, key, itemStack);
    }

    public static boolean storeStringData(final @NotNull String stringData, final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.STRING, stringData, key, itemStack);
    }

    public static boolean addItemKey(final @NotNull NamespacedKey itemKey, final @NotNull ItemStack itemStack) {
        return storeStringData(itemKey.toString(), ITEMSTACK_ITEMKEY_STORAGE, itemStack);
    }

    public static boolean addUid(final @NotNull ItemStack itemStack) {
        return storeStringData(UUID.randomUUID().toString(), ITEMSTACK_UID_STORAGE, itemStack);
    }

    public static boolean addRelatedItemStackUid(final @NotNull PersistentDataHolder persistentDataHolder, final @NotNull UUID itemStackUid) {
        try {
            persistentDataHolder.getPersistentDataContainer().set(DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE, PersistentDataType.STRING, itemStackUid.toString());
        } catch (final Exception e) {
            Bukkit.getLogger().severe(e.toString());
            return false;
        }

        return true;
    }

    public static boolean addRelatedItemStackUid(final @NotNull PersistentDataHolder persistentDataHolder, final @NotNull ItemStack holderRelatedItemStack) {
        final UUID itemUid = getUid(holderRelatedItemStack);

        if (itemUid == null)
            return false;

        return addRelatedItemStackUid(persistentDataHolder, itemUid);
    }

    public static <Primitive, Complex> @Nullable Complex getDataOfType(final @NotNull PersistentDataType<Primitive, Complex> type, final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        if (itemStack.getType() == Material.AIR)
            return null;

        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        return itemMeta.getPersistentDataContainer().get(key, type);
    }

    public static <Primitive, Complex> @NotNull Complex getDataOfType(final @NotNull PersistentDataType<Primitive, Complex> type, final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull Complex defaultReturnValue) {
        final Complex getDataResult = getDataOfType(type, key, itemStack);

        return (getDataResult == null) ? defaultReturnValue : getDataResult;
    }

    public static @Nullable Boolean getBooleanData(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.BOOLEAN, key, itemStack);
    }

    public static @Nullable Integer getIntegerData(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.INTEGER, key, itemStack);
    }

    public static @Nullable String getStringData(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.STRING, key, itemStack);
    }

    public static @NotNull Boolean getBooleanData(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull Boolean defaultReturnValue) {
        return getDataOfType(PersistentDataType.BOOLEAN, key, itemStack, defaultReturnValue);
    }

    public static @NotNull Integer getIntegerData(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull Integer defaultReturnValue) {
        return getDataOfType(PersistentDataType.INTEGER, key, itemStack, defaultReturnValue);
    }

    public static @NotNull String getStringData(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull String defaultReturnValue) {
        return getDataOfType(PersistentDataType.STRING, key, itemStack, defaultReturnValue);
    }

    public static @Nullable NamespacedKey getFoxItemKey(final @NotNull ItemStack itemStack) {
        final String itemKey = getStringData(ITEMSTACK_ITEMKEY_STORAGE, itemStack);

        if (itemKey == null)
            return null;

        return NamespacedKey.fromString(itemKey);
    }

    public static @Nullable UUID getUid(final @NotNull ItemStack itemStack) {
        final String itemUid = getStringData(ITEMSTACK_UID_STORAGE, itemStack);

        return (itemUid == null) ? null : UUID.fromString(itemUid);
    }

    public static @Nullable UUID getRelatedItemUid(final @NotNull PersistentDataHolder persistentDataHolder) {
        final String uidInStorage = persistentDataHolder.getPersistentDataContainer().get(DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE, PersistentDataType.STRING);

        return (uidInStorage == null) ? null : UUID.fromString(uidInStorage);
    }

    /**
     * @param key               Location to where the cooldown should be and/or is stored.
     * @param itemStack         ItemStack of which the cooldown applies to.
     * @param cooldownInSeconds The cooldown in seconds.
     * @return True if there is a cooldown or False if there is not.
     */
    public static boolean getCooldown(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull Long cooldownInSeconds) {
        final long timestampOfNowInNanoseconds = System.nanoTime();

        //   (timestampOfNowInNanoseconds - timestampOfLastCooldownInNanoseconds) > (cooldownInSeconds * 1000000000)
        //   timeSinceLastCooldownInNanoseconds > cooldownInNanoseconds
        if ((timestampOfNowInNanoseconds - getDataOfType(PersistentDataType.LONG, key, itemStack, 0L)) > (cooldownInSeconds * 1000000000)) {
            storeDataOfType(PersistentDataType.LONG, timestampOfNowInNanoseconds, key, itemStack);
            return false;
        }

        return true;
    }

    public static boolean getCooldown(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull Long cooldownInSeconds, final @NotNull Player player, final @NotNull BaseComponent successMessage, final @NotNull BaseComponent unsuccessfulMessage) {
        final boolean cooldownActive = getCooldown(key, itemStack, cooldownInSeconds);

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.75F);

        if (cooldownActive) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, unsuccessfulMessage);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 0.5F);
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, successMessage);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1F);
        }

        return cooldownActive;
    }

    public static boolean getCooldown(final @NotNull NamespacedKey key, final @NotNull ItemStack itemStack, final @NotNull Long cooldownInSeconds, final @NotNull Player player, final @NotNull BaseComponent successMessage) {
        return getCooldown(key, itemStack, cooldownInSeconds, player, successMessage, COOLDOWN_PRESENT_MESSAGE);
    }

    private ItemUtils() {
    }
}
